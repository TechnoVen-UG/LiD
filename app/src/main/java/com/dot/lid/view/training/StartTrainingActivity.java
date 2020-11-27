package com.dot.lid.view.training;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dot.lid.R;
import com.dot.lid.app.Injection;
import com.dot.lid.app.MyApplication;
import com.dot.lid.app.ViewModelProviderFactory;
import com.dot.lid.data.Resource;
import com.dot.lid.databinding.ActivityStartTrainingBinding;
import com.dot.lid.dialog.InstructionDialog;
import com.dot.lid.dialog.ShowAnswerDialog;
import com.dot.lid.model.Achievement;
import com.dot.lid.model.CacheQuestion;
import com.dot.lid.model.Question;
import com.dot.lid.model.Selection;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.dot.lid.utils.AchieveData.getHistoryAchievements;
import static com.dot.lid.utils.AchieveData.getHumanityAchievements;
import static com.dot.lid.utils.AchieveData.getPoliticsAchievements;
import static com.dot.lid.utils.AchieveData.getStateAchievements;
import static com.dot.lid.utils.Constant.ALL_QUESTION_KEY;
import static com.dot.lid.utils.Constant.LANGUAGE_NAME_KEY;
import static com.dot.lid.utils.Constant.MAIN_CATEGORY_KEY;
import static com.dot.lid.utils.Constant.SUB_CATEGORY_KEY;
import static com.dot.lid.utils.Constant.getAchievement;
import static com.dot.lid.utils.DatabaseToken.COLL_HISTORY_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.COLL_HUMANITY_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.COLL_POLITICS_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.COLL_STATE_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.DOC_BUNDESLANDER;
import static com.dot.lid.utils.DatabaseToken.DOC_GESCHICHTE_UND_VERANTWORTUNG;
import static com.dot.lid.utils.DatabaseToken.DOC_MENSCH_UND_GESELLSCHAFT;
import static com.dot.lid.utils.DatabaseToken.DOC_POLITIK_IN_DER_DEMOKARITE;
import static com.dot.lid.utils.Language.ARABIC;
import static com.dot.lid.utils.Language.ENGLISH;
import static com.dot.lid.utils.Language.GERMAN;

public class StartTrainingActivity extends AppCompatActivity implements ShowAnswerDialog.ShowAnswerInterface
        , InstructionDialog.InstructionDialogInterface {
    private ActivityStartTrainingBinding binding;
    private MyApplication myApplication;
    private ShowAnswerDialog answerDialog;
    private ProgressDialog progressDialog;
    private StartTrainingViewModel viewModel;
    private int selectedNumber = 0;
    private int questionCounter = 0;
    private String mainCategory;
    private String subCategory;
    private String languageName;
    private boolean isAllQuestion;
    private List<Question> questionList;
    private List<Selection> answeredQuestionList;
    private MediaPlayer mediaPlayer;
    private InstructionDialog instructionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartTrainingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        languageName = getIntent().getStringExtra(LANGUAGE_NAME_KEY);
        mainCategory = getIntent().getStringExtra(MAIN_CATEGORY_KEY);
        subCategory = getIntent().getStringExtra(SUB_CATEGORY_KEY);
        isAllQuestion = getIntent().getBooleanExtra(ALL_QUESTION_KEY, false);

        myApplication = MyApplication.getInstance();
        String currentLanguage = myApplication.getLanguage();
        if (currentLanguage.equals(ARABIC.getLanguage())) {
            updateResources(ARABIC.getLanguage());
        } else if (currentLanguage.equals(GERMAN.getLanguage())) {
            updateResources(GERMAN.getLanguage());
        } else {
            updateResources(ENGLISH.getLanguage());
        }

        setSupportActionBar(binding.toolbar.toolbar);
        ViewModelProviderFactory providerFactory = Injection.provideViewModelProviderFactory(this);
        viewModel = new ViewModelProvider(this, providerFactory).get(StartTrainingViewModel.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        subscribeObserver();
        retrieveQuestion();

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }

    private void subscribeObserver() {
        viewModel.observeTrainingQuestion().observe(this, new Observer<Resource<List<Question>>>() {
            @Override
            public void onChanged(Resource<List<Question>> resource) {
                if (resource != null) {
                    switch (resource.status) {
                        case LOADING:
                            progressDialog.show();
                            break;
                        case ERROR:
                            progressDialog.dismiss();
                            Snackbar.make(binding.parent, R.string.something_went_wrong, BaseTransientBottomBar.LENGTH_LONG).show();
                            break;
                        case SUCCESS:
                            questionList = resource.data;
                            if (isAllQuestion) {
                                viewModel.retrieveCacheQuestion(subCategory.concat("_all"));
                            } else {
                                viewModel.retrieveCacheQuestion(subCategory);
                            }
                            break;
                    }
                }
            }
        });

        viewModel.observeDeletedCacheQuestion().observe(this, new Observer<Resource<Boolean>>() {
            @Override
            public void onChanged(Resource<Boolean> resource) {
                if (resource != null) {
                    if (resource.status == Resource.Status.SUCCESS) {
                        viewModel.insertAchievement(getAchievementList());
                    }
                }
            }
        });

        viewModel.observeInsertedAchievement().observe(this, new Observer<Resource<Boolean>>() {
            @Override
            public void onChanged(Resource<Boolean> resource) {
                if (resource != null) {
                    if (resource.status == Resource.Status.SUCCESS) {
                        if (MyApplication.getInstance().getShowAnswerState()) {
                            showAnswerDialog();
                        } else {
                            showPlayAgainDialog();
                        }
                    }
                }
            }
        });

        viewModel.observeRetrieveCacheQuestion().observe(this, new Observer<Resource<List<CacheQuestion>>>() {
            @Override
            public void onChanged(Resource<List<CacheQuestion>> resource) {
                if (resource != null) {
                    switch (resource.status) {
                        case ERROR:
                            startNewTraining();
                            break;
                        case SUCCESS:
                            assert resource.data != null;
                            if (resource.data.size() > 0) {
                                showContinueDialog(resource.data);
                            } else {
                                startNewTraining();
                            }
                            break;
                    }
                }
            }
        });
    }

    private void showContinueDialog(final List<CacheQuestion> cacheQuestionList) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = getLayoutInflater().inflate(R.layout.layout_exit_app_dialog, viewGroup, false);
        Button yesBT = view.findViewById(R.id.exitYesButton);
        Button noBT = view.findViewById(R.id.exitNoButton);
        ImageView closeBT = view.findViewById(R.id.closeButton);
        TextView title = view.findViewById(R.id.exitTitleTV);
        title.setText(R.string.continue_training_q);
        yesBT.setText(R.string.continue_training);
        noBT.setText(R.string.new_training);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        yesBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startContinueTraining(cacheQuestionList);
            }
        });
        noBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startNewTraining();
            }
        });
        closeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void startContinueTraining(List<CacheQuestion> cacheQuestionList) {
        assert questionList != null;
        answeredQuestionList = new ArrayList<>();
        questionCounter = cacheQuestionList.size();
        selectedNumber = 0;
        if (questionCounter < questionList.size()) {
            for (int i = 0; i < cacheQuestionList.size(); i++) {
                CacheQuestion cacheQuestion = cacheQuestionList.get(i);
                Question question = questionList.get(i);
                Selection selection = new Selection(cacheQuestion.getSelectedNo(), question);
                answeredQuestionList.add(selection);
            }
            progressDialog.dismiss();
            binding.parent.setVisibility(View.VISIBLE);
            updateUI();
        } else {
            progressDialog.dismiss();
            startNewTraining();
        }
    }

    private void startNewTraining() {
        assert questionList != null;
        answeredQuestionList = new ArrayList<>();
        questionCounter = 0;
        selectedNumber = 0;
        progressDialog.dismiss();
        binding.parent.setVisibility(View.VISIBLE);
        binding.continueBT.setBackgroundResource(R.drawable.ubable_button);
        binding.continueBT.setEnabled(false);
        updateUI();
    }

    private void updateQuestionCounter() {
        if (questionCounter >= questionList.size() - 1) {
            addToAnswered();
            if (isAllQuestion) {
                viewModel.deleteCacheQuestion(subCategory.concat("_all"));
            } else {
                viewModel.deleteCacheQuestion(subCategory);
            }
        } else {
            binding.continueBT.setBackgroundResource(R.drawable.ubable_button);
            binding.continueBT.setEnabled(false);
            addToAnswered();
            questionCounter += 1;
            selectedNumber = 0;
            updateUI();
        }
    }

    private void addToAnswered() {
        Selection selection = new Selection(selectedNumber, questionList.get(questionCounter));
        answeredQuestionList.add(selection);
        CacheQuestion cacheQuestion = new CacheQuestion();
        if (isAllQuestion) {
            cacheQuestion.setCategoryName(subCategory.concat("_all"));
        } else {
            cacheQuestion.setCategoryName(subCategory);
        }
        cacheQuestion.setQuestionNo(questionCounter);
        cacheQuestion.setSelectedNo(selectedNumber);
        viewModel.insertCacheQuestion(cacheQuestion);
    }

    private void updateUI() {
        clearBox();
        Question question = questionList.get(questionCounter);
        if (question.isHasImage()) {
            loadImage(question.getImageUrl());
        } else {
            binding.imageProgressbar.setVisibility(View.GONE);
            binding.questionIV.setVisibility(View.GONE);
        }
        binding.testTV.setText(question.getCategory());
        binding.categoryTV.setText(question.getSubCategory());
        binding.questionTV.setText(question.getQuestion());
        binding.optionOneTV.setText(question.getAnswer1());
        binding.optionTwoTV.setText(question.getAnswer2());
        binding.optionThreeTV.setText(question.getAnswer3());
        binding.optionFourTV.setText(question.getAnswer4());
        binding.countTV.setText(questionCounter + 1 + "/" + questionList.size());
    }

    private void clearBox() {
        binding.optionOneIV.setImageResource(R.drawable.one);
        binding.optionOneTV.setBackgroundResource(R.drawable.spinner_background);
        binding.optionTwoIV.setImageResource(R.drawable.two);
        binding.optionTwoTV.setBackgroundResource(R.drawable.spinner_background);
        binding.optionThreeIV.setImageResource(R.drawable.three);
        binding.optionThreeTV.setBackgroundResource(R.drawable.spinner_background);
        binding.optionFourIV.setImageResource(R.drawable.four);
        binding.optionFourTV.setBackgroundResource(R.drawable.spinner_background);
    }

    private void loadImage(String imageUrl) {
        binding.questionIV.setVisibility(View.VISIBLE);
        binding.imageProgressbar.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(imageUrl)
                .into(binding.questionIV, new Callback() {
                    @Override
                    public void onSuccess() {
                        binding.imageProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        binding.imageProgressbar.setVisibility(View.GONE);
                    }
                });
    }

    private void checkAnswer() {
        boolean soundSate = MyApplication.getInstance().getSoundSate();
        boolean showAnswerState = MyApplication.getInstance().getShowAnswerState();
        Question question = questionList.get(questionCounter);
        int correctAns = question.getCorrectAnswer();
        if (showAnswerState) {
            switch (correctAns) {
                case 1:
                    binding.optionOneIV.setImageResource(R.drawable.correct);
                    binding.optionOneTV.setBackgroundResource(R.drawable.correct_background);
                    break;
                case 2:
                    binding.optionTwoIV.setImageResource(R.drawable.correct);
                    binding.optionTwoTV.setBackgroundResource(R.drawable.correct_background);
                    break;
                case 3:
                    binding.optionThreeIV.setImageResource(R.drawable.correct);
                    binding.optionThreeTV.setBackgroundResource(R.drawable.correct_background);
                    break;
                case 4:
                    binding.optionFourIV.setImageResource(R.drawable.correct);
                    binding.optionFourTV.setBackgroundResource(R.drawable.correct_background);
                    break;
            }
            if ((selectedNumber == 1) && (selectedNumber != correctAns)) {
                binding.optionOneIV.setImageResource(R.drawable.incorrect);
                binding.optionOneTV.setBackgroundResource(R.drawable.incorrect_background);
            } else if ((selectedNumber == 2) && (selectedNumber != correctAns)) {
                binding.optionTwoIV.setImageResource(R.drawable.incorrect);
                binding.optionTwoTV.setBackgroundResource(R.drawable.incorrect_background);
            } else if ((selectedNumber == 3) && (selectedNumber != correctAns)) {
                binding.optionThreeIV.setImageResource(R.drawable.incorrect);
                binding.optionThreeTV.setBackgroundResource(R.drawable.incorrect_background);
            } else if ((selectedNumber == 4) && (selectedNumber != correctAns)) {
                binding.optionFourIV.setImageResource(R.drawable.incorrect);
                binding.optionFourTV.setBackgroundResource(R.drawable.incorrect_background);
            }
        }

        if (soundSate) {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
            if ((selectedNumber != correctAns) && showAnswerState) {
                mediaPlayer = MediaPlayer.create(this, R.raw.wrong);
            } else {
                mediaPlayer = MediaPlayer.create(this, R.raw.correct);
            }
            mediaPlayer.start();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        binding.toolbar.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.continueBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestionCounter();
            }
        });

        binding.endBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.optionOneIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedNumber == 0) {
                    changeOption(1);
                    selectedNumber = 1;
                    checkAnswer();
                }
            }
        });

        binding.optionTwoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedNumber == 0) {
                    changeOption(2);
                    selectedNumber = 2;
                    checkAnswer();
                }
            }
        });

        binding.optionThreeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedNumber == 0) {
                    changeOption(3);
                    selectedNumber = 3;
                    checkAnswer();
                }
            }
        });

        binding.optionFourIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedNumber == 0) {
                    changeOption(4);
                    selectedNumber = 4;
                    checkAnswer();
                }
            }
        });
    }

    private void retrieveQuestion() {
        viewModel.getTrainingQuestion(languageName, mainCategory, subCategory, isAllQuestion);
    }

    private void updateResources(String language) {
        Locale locale = new Locale(language);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private void showOptionDialog() {
        final Switch soundSW, showAnsSW;
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = getLayoutInflater().inflate(R.layout.layout_option_dialog, viewGroup, false);
        soundSW = view.findViewById(R.id.soundSW);
        showAnsSW = view.findViewById(R.id.showAnsSW);
        ImageView cancelBT = view.findViewById(R.id.cancelBT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        if (myApplication.getSoundSate()) {
            soundSW.setChecked(true);
            soundSW.setText(R.string.on);
        } else {
            soundSW.setChecked(false);
            soundSW.setText(R.string.off);
        }

        if (myApplication.getShowAnswerState()) {
            showAnsSW.setChecked(true);
            showAnsSW.setText(R.string.on);
        } else {
            showAnsSW.setChecked(false);
            showAnsSW.setText(R.string.off);
        }

        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        soundSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    myApplication.setSoundState(true);
                    soundSW.setText(R.string.on);
                } else {
                    myApplication.setSoundState(false);
                    soundSW.setText(R.string.off);
                }
            }
        });

        showAnsSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    myApplication.setShowAnswerState(true);
                    showAnsSW.setText(R.string.on);
                } else {
                    myApplication.setShowAnswerState(false);
                    showAnsSW.setText(R.string.off);
                }
            }
        });
    }

    private void showPlayAgainDialog() {
        binding.parent.setVisibility(View.INVISIBLE);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = getLayoutInflater().inflate(R.layout.layout_exit_app_dialog, viewGroup, false);
        Button yesBT = view.findViewById(R.id.exitYesButton);
        Button noBT = view.findViewById(R.id.exitNoButton);
        ImageView closeBT = view.findViewById(R.id.closeButton);
        TextView title = view.findViewById(R.id.exitTitleTV);
        title.setText(R.string.play_again);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        yesBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startNewTraining();
            }
        });
        noBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                finish();

            }
        });
        closeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                finish();
            }
        });
    }

    private void showAnswerDialog() {
        binding.parent.setVisibility(View.INVISIBLE);
        answerDialog = new ShowAnswerDialog(answeredQuestionList);
        answerDialog.show(getSupportFragmentManager(), "AnswerDialog");
    }

    private void changeOption(int position) {
        binding.continueBT.setBackgroundResource(R.drawable.yes_button);
        binding.continueBT.setEnabled(true);
        switch (position) {
            case 1:
                binding.optionOneIV.setImageResource(R.drawable.check_box);
                binding.optionTwoIV.setImageResource(R.drawable.two);
                binding.optionThreeIV.setImageResource(R.drawable.three);
                binding.optionFourIV.setImageResource(R.drawable.four);
                break;
            case 2:
                binding.optionTwoIV.setImageResource(R.drawable.check_box);
                binding.optionOneIV.setImageResource(R.drawable.one);
                binding.optionThreeIV.setImageResource(R.drawable.three);
                binding.optionFourIV.setImageResource(R.drawable.four);
                break;
            case 3:
                binding.optionThreeIV.setImageResource(R.drawable.check_box);
                binding.optionOneIV.setImageResource(R.drawable.one);
                binding.optionTwoIV.setImageResource(R.drawable.two);
                binding.optionFourIV.setImageResource(R.drawable.four);
                break;
            case 4:
                binding.optionFourIV.setImageResource(R.drawable.check_box);
                binding.optionOneIV.setImageResource(R.drawable.one);
                binding.optionTwoIV.setImageResource(R.drawable.two);
                binding.optionThreeIV.setImageResource(R.drawable.three);
                break;
        }
    }

    @Override
    public void onAnswerDialogCancel() {
        answerDialog.dismiss();
        showPlayAgainDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start_training, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.startTrainingInstruction:
                instructionDialog = new InstructionDialog();
                instructionDialog.show(getSupportFragmentManager(), "InstructionDialog");
                return true;
            case R.id.startTrainingOptions:
                showOptionDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onInstructionDialogCancel() {
        instructionDialog.dismiss();
    }

    @Override
    protected void onPause() {
        binding.adView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        binding.adView.resume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        binding.adView.destroy();
        super.onDestroy();
    }

    private List<Achievement> getAchievementList() {
        if (isAllQuestion) {
            List<Selection> stateList = new ArrayList<>();
            List<Selection> politicsList = new ArrayList<>();
            List<Selection> humanityList = new ArrayList<>();
            List<Selection> historyList = new ArrayList<>();
            List<Achievement> finalList = new ArrayList<>();
            for (Selection selection : answeredQuestionList) {
                if (selection.getQuestion().getCategory().equals(getResources().getString(R.string.federal_states))) {
                    stateList.add(selection);
                } else if (selection.getQuestion().getCategory().equals(getResources().getString(R.string.politics_in_the_democracy))) {
                    politicsList.add(selection);
                } else if (selection.getQuestion().getCategory().equals(getResources().getString(R.string.history_and_responsibility))) {
                    historyList.add(selection);
                } else if (selection.getQuestion().getCategory().equals(getResources().getString(R.string.humanity_and_society))) {
                    humanityList.add(selection);
                }
            }
            finalList.addAll(getAchievementByResult(stateList));
            finalList.addAll(getPoliticsAchievements(getResources(), politicsList));
            finalList.addAll(getHumanityAchievements(getResources(), humanityList));
            finalList.addAll(getHistoryAchievements(getResources(), historyList));
            return finalList;

        } else if (mainCategory.equals(DOC_BUNDESLANDER) && subCategory.equals(COLL_STATE_ALL_QUESTION)) {
            return getStateAchievements(getResources(), answeredQuestionList);
        } else if (mainCategory.equals(DOC_POLITIK_IN_DER_DEMOKARITE) && subCategory.equals(COLL_POLITICS_ALL_QUESTION)) {
            return getPoliticsAchievements(getResources(), answeredQuestionList);
        } else if (mainCategory.equals(DOC_GESCHICHTE_UND_VERANTWORTUNG) && subCategory.equals(COLL_HISTORY_ALL_QUESTION)) {
            return getHistoryAchievements(getResources(), answeredQuestionList);
        } else if (mainCategory.equals(DOC_MENSCH_UND_GESELLSCHAFT) && subCategory.equals(COLL_HUMANITY_ALL_QUESTION)) {
            return getHumanityAchievements(getResources(), answeredQuestionList);
        } else {
            return getAchievementByResult(answeredQuestionList);
        }
    }

    private List<Achievement> getAchievementByResult(List<Selection> selectionList) {
        List<Achievement> achievementList = new ArrayList<>();
        float count = 0;

        for (Selection selection : selectionList) {
            if (selection.getSelectionNumber() == selection.getQuestion().getCorrectAnswer()) {
                count++;
            }
        }

        float percentage = 0;
        if (count != 0) {
            percentage = (count / selectionList.size()) * 100;
        }

        Achievement achievement = getAchievement(subCategory);

        assert achievement != null;
        if (percentage >= 50 && percentage < 70) {
            achievement.setHasBronze(true);
        } else if (percentage >= 70 && percentage < 90) {
            achievement.setHasSilver(true);
        } else if (percentage >= 90 && percentage < 100) {
            achievement.setHasGold(true);
        } else if (percentage >= 100) {
            achievement.setHasPlatinum(true);
        }
        achievementList.add(achievement);
        return achievementList;
    }

}