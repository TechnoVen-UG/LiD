package com.dot.lid.view.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.dot.lid.databinding.ActivityTestBinding;
import com.dot.lid.dialog.InstructionDialog;
import com.dot.lid.dialog.ShowAnswerDialog;
import com.dot.lid.dialog.TestCompleteDialog;
import com.dot.lid.model.Question;
import com.dot.lid.model.Selection;
import com.dot.lid.model.TestResult;
import com.dot.lid.view.training.AchievementActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.dot.lid.utils.Constant.TEST_ACTIVITY_KEY;
import static com.dot.lid.utils.Constant.getCurrentLanguage;
import static com.dot.lid.utils.Language.ARABIC;
import static com.dot.lid.utils.Language.ENGLISH;
import static com.dot.lid.utils.Language.GERMAN;

public class TestActivity extends AppCompatActivity
        implements TestCompleteDialog.TestDialogListener,
        ShowAnswerDialog.ShowAnswerInterface, InstructionDialog.InstructionDialogInterface {

    private static final long START_TIME_IN_MILLIS = 1800000;
    private static long timeLeftInMillis;
    private TestCompleteDialog completeDialog;
    private ShowAnswerDialog answerDialog;
    private ActivityTestBinding binding;
    private MyApplication myApplication;
    private AnimationDrawable animationDrawable;
    private String currentLanguage;
    private TestViewModel viewModel;
    private int selectionNumber;
    private int questionCounter;
    private int testResult;
    private String stateName;
    private ProgressDialog progressDialog;
    private List<Question> questions;
    private List<Selection> selectionList;
    private MediaPlayer mediaPlayer;
    private InstructionDialog instructionDialog;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stateName = getIntent().getStringExtra(TEST_ACTIVITY_KEY);

        myApplication = MyApplication.getInstance();
        currentLanguage = myApplication.getLanguage();
        if (currentLanguage.equals(ARABIC.getLanguage())) {
            updateResources(ARABIC.getLanguage());
        } else if (currentLanguage.equals(GERMAN.getLanguage())) {
            updateResources(GERMAN.getLanguage());
        } else {
            updateResources(ENGLISH.getLanguage());
        }

        setSupportActionBar(binding.toolbar.toolbar);
        ViewModelProviderFactory providerFactory = Injection.provideViewModelProviderFactory(this);
        viewModel = new ViewModelProvider(this, providerFactory).get(TestViewModel.class);
        binding.countdownIV.setBackgroundResource(R.drawable.countdown_anim);
        animationDrawable = (AnimationDrawable) binding.countdownIV.getBackground();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        showStartDialog();
        subscribeObserver();

        binding.adView.loadAd(new AdRequest.Builder().build());
        binding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startTimer();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                countDownTimer.cancel();
            }
        });
    }

    private void showStartDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = getLayoutInflater().inflate(R.layout.layout_exit_app_dialog, viewGroup, false);
        Button yesBT = view.findViewById(R.id.exitYesButton);
        Button noBT = view.findViewById(R.id.exitNoButton);
        ImageView closeBT = view.findViewById(R.id.closeButton);
        TextView title = view.findViewById(R.id.exitTitleTV);
        title.setText(R.string.start_model_test_now);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        yesBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                viewModel.getQuestion(getCurrentLanguage(currentLanguage), stateName);
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

    private void subscribeObserver() {
        viewModel.observeTestQuestion().observe(this, new Observer<Resource<List<Question>>>() {
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
                            progressDialog.dismiss();
                            questions = resource.data;
                            init();
                            startTest();
                            updateUI();
                            break;
                    }
                }
            }
        });

        viewModel.observeInsertedResult().observe(this, new Observer<Resource<Boolean>>() {
            @Override
            public void onChanged(Resource<Boolean> resource) {
                if (resource != null) {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case ERROR:
                            showTestResult();
                            break;
                        case SUCCESS:
                            if (resource.data) {
                                showTestResult();
                            }
                            break;
                    }
                }
            }
        });
    }

    private void startTest() {
        binding.parent.setVisibility(View.VISIBLE);
        timeLeftInMillis = START_TIME_IN_MILLIS;
        startTimer();
        animationDrawable.start();
    }

    private void init() {
        selectionNumber = 0;
        questionCounter = 0;
        selectionList = new ArrayList<>();
        for (int i = 0; i < 33; i++) {
            selectionList.add(new Selection());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.toolbar.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExitDialog();
            }
        });

        binding.previousBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePreviousState();
            }
        });

        binding.continueBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestionCounter();
            }
        });

        binding.optionOneIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeOption(1);
                playMusic();
                selectionNumber = 1;
            }
        });

        binding.optionTwoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeOption(2);
                playMusic();
                selectionNumber = 2;
            }
        });

        binding.optionThreeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeOption(3);
                playMusic();
                selectionNumber = 3;
            }
        });

        binding.optionFourIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeOption(4);
                playMusic();
                selectionNumber = 4;
            }
        });
    }

    private void updateUI() {
        binding.imageProgressbar.setVisibility(View.GONE);
        clearBox();
        if (questions == null || questionCounter < 0) return;
        Question question = questions.get(questionCounter);
        if (question.isHasImage()) {
            loadImage(question.getImageUrl());
        } else {
            binding.questionImage.setVisibility(View.GONE);
        }
        Selection selection = selectionList.get(questionCounter);
        int selectionNumber = selection.getSelectionNumber();
        if (selectionNumber != 0) {
            changeOption(selectionNumber);
            this.selectionNumber = selectionNumber;
        }
        binding.questionTV.setText(question.getQuestion());
        binding.categoryTV.setText(question.getCategory());
        binding.optionOneTV.setText(question.getAnswer1());
        binding.optionTwoTV.setText(question.getAnswer2());
        binding.optionThreeTV.setText(question.getAnswer3());
        binding.optionFourTV.setText(question.getAnswer4());
        binding.countTV.setText(questionCounter + 1 + "/" + questions.size());
    }

    private void updateQuestionCounter() {
        if (questionCounter >= 32) {
            countDownTimer.cancel();
            binding.continueBT.setBackgroundResource(R.drawable.ubable_button);
            binding.continueBT.setEnabled(false);
            updateSelection();
            testCompleted();
        } else {
            binding.continueBT.setBackgroundResource(R.drawable.ubable_button);
            binding.continueBT.setEnabled(false);
            binding.previousBT.setBackgroundResource(R.drawable.no_button);
            binding.previousBT.setEnabled(true);
            updateSelection();
            questionCounter += 1;
            selectionNumber = 0;
            updateUI();
        }
    }

    private void updateSelection() {
        if (questions != null) {
            Selection selection = new Selection(selectionNumber, questions.get(questionCounter));
            selectionList.set(questionCounter, selection);
        }
    }

    private void updatePreviousState() {
        questionCounter -= 1;
        if (questionCounter < 0) {
            return;
        } else if (questionCounter == 0) {
            updateUI();
            binding.previousBT.setBackgroundResource(R.drawable.ubable_button);
            binding.previousBT.setEnabled(false);
            binding.continueBT.setBackgroundResource(R.drawable.yes_button);
            binding.continueBT.setEnabled(true);
        } else {
            updateUI();
            binding.continueBT.setBackgroundResource(R.drawable.yes_button);
            binding.continueBT.setEnabled(true);
        }
    }

    private void testCompleted() {
        binding.parent.setVisibility(View.INVISIBLE);
        checkTestResult();
        TestResult testResult = new TestResult();
        testResult.setMark(this.testResult);
        viewModel.insertTestResult(testResult);
    }

    private void showTestResult() {
        boolean showAnswerState = MyApplication.getInstance().getShowAnswerState();
        if (showAnswerState) {
            answerDialog = new ShowAnswerDialog(selectionList);
            answerDialog.show(getSupportFragmentManager(), "AnswerDialog");
        } else {
            showCompleteDialog();
        }
    }

    private void showCompleteDialog() {
        Bundle arg = new Bundle();
        arg.putString(TestCompleteDialog.TEST_DIALOG_KEY, "" + testResult + "/33");
        completeDialog = new TestCompleteDialog();
        completeDialog.setArguments(arg);
        completeDialog.show(getSupportFragmentManager(), "TestDialog");
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                testCompleted();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        binding.time.setText(timeLeftFormatted);
    }


    private void changeOption(int position) {
        binding.continueBT.setBackgroundResource(R.drawable.yes_button);
        binding.continueBT.setEnabled(true);

        switch (position) {
            case 1:
                binding.optionOneIV.setImageResource(R.drawable.check_box);
                binding.optionTwoIV.setImageResource(R.drawable.box);
                binding.optionThreeIV.setImageResource(R.drawable.box);
                binding.optionFourIV.setImageResource(R.drawable.box);
                break;
            case 2:
                binding.optionTwoIV.setImageResource(R.drawable.check_box);
                binding.optionOneIV.setImageResource(R.drawable.box);
                binding.optionThreeIV.setImageResource(R.drawable.box);
                binding.optionFourIV.setImageResource(R.drawable.box);
                break;
            case 3:
                binding.optionThreeIV.setImageResource(R.drawable.check_box);
                binding.optionOneIV.setImageResource(R.drawable.box);
                binding.optionTwoIV.setImageResource(R.drawable.box);
                binding.optionFourIV.setImageResource(R.drawable.box);
                break;
            case 4:
                binding.optionFourIV.setImageResource(R.drawable.check_box);
                binding.optionOneIV.setImageResource(R.drawable.box);
                binding.optionTwoIV.setImageResource(R.drawable.box);
                binding.optionThreeIV.setImageResource(R.drawable.box);
                break;
        }
    }


    private void playMusic() {
        boolean soundSate = MyApplication.getInstance().getSoundSate();
        if (soundSate) {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(this, R.raw.correct);
            mediaPlayer.start();
        }
    }


    private void loadImage(String imageUrl) {
        binding.questionImage.setVisibility(View.VISIBLE);
        binding.imageProgressbar.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(imageUrl)
                .into(binding.questionImage, new Callback() {
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

    private void clearBox() {
        binding.optionOneIV.setImageResource(R.drawable.box);
        binding.optionTwoIV.setImageResource(R.drawable.box);
        binding.optionThreeIV.setImageResource(R.drawable.box);
        binding.optionFourIV.setImageResource(R.drawable.box);
    }


    private void checkTestResult() {
        testResult = 0;
        List<Selection> toRemoveList = new ArrayList<>();
        if (questionCounter < 1) {
            toRemoveList.addAll(selectionList);
        } else {
            for (Selection selection : selectionList) {
                try {
                    int selectionNumber = selection.getSelectionNumber();
                    if (selectionNumber == 0) {
                        toRemoveList.add(selection);
                    } else if (selectionNumber > 0) {
                        if (selectionNumber == selection.getQuestion().getCorrectAnswer()) {
                            testResult += 1;
                        }
                    }
                } catch (NullPointerException e) {

                }
            }
        }
        selectionList.removeAll(toRemoveList);
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

    private void showExitDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = getLayoutInflater().inflate(R.layout.layout_exit_app_dialog, viewGroup, false);
        Button yesBT = view.findViewById(R.id.exitYesButton);
        Button noBT = view.findViewById(R.id.exitNoButton);
        ImageView closeBT = view.findViewById(R.id.closeButton);
        TextView title = view.findViewById(R.id.exitTitleTV);
        title.setText(R.string.really_cancel_test);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.create();
        alertDialog.show();

        yesBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                finish();
            }
        });
        noBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        closeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onAnswerDialogCancel() {
        answerDialog.dismiss();
        showCompleteDialog();
    }

    @Override
    public void onYes() {
        completeDialog.dismiss();
        viewModel.getQuestion(getCurrentLanguage(currentLanguage), stateName);
    }

    @Override
    public void onNo() {
        completeDialog.dismiss();
        finish();
    }

    @Override
    public void onShare() {
        String url = MyApplication.appLink;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Shared to :"));
    }

    @Override
    public void onBarChart() {
        startActivity(new Intent(TestActivity.this, BarChartActivity.class));
        showAnimation();
    }

    @Override
    public void onAchievement() {
        startActivity(new Intent(TestActivity.this, AchievementActivity.class));
        showAnimation();
    }

    @Override
    public void onCancel() {
        completeDialog.dismiss();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.testInstruction:
                instructionDialog = new InstructionDialog();
                instructionDialog.show(getSupportFragmentManager(), "InstructionDialog");
                return true;
            case R.id.testOptions:
                showOptionDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private void showAnimation() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
}