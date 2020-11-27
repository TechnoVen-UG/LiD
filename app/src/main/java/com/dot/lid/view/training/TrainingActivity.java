package com.dot.lid.view.training;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dot.lid.R;
import com.dot.lid.adapter.FederalSpinnerAdapter;
import com.dot.lid.adapter.HistorySpinnerAdapter;
import com.dot.lid.adapter.LanguageSpinnerAdapter;
import com.dot.lid.adapter.PoliticalSpinnerAdapter;
import com.dot.lid.adapter.SocietySpinnerAdapter;
import com.dot.lid.adapter.TrainingStateSpinnerAdapter;
import com.dot.lid.app.MyApplication;
import com.dot.lid.databinding.ActivityTrainingBinding;
import com.dot.lid.dialog.InstructionDialog;
import com.dot.lid.view.test.BarChartActivity;

import java.util.Locale;

import static com.dot.lid.utils.Constant.ALL_QUESTION_KEY;
import static com.dot.lid.utils.Constant.LANGUAGE_NAME_KEY;
import static com.dot.lid.utils.Constant.MAIN_CATEGORY_KEY;
import static com.dot.lid.utils.Constant.SUB_CATEGORY_KEY;
import static com.dot.lid.utils.Constant.TRAINING_ACTIVITY_KEY;
import static com.dot.lid.utils.Constant.getCurrentLanguage;
import static com.dot.lid.utils.Constant.getFederalList;
import static com.dot.lid.utils.Constant.getFederalStateItem;
import static com.dot.lid.utils.Constant.getHistoryItem;
import static com.dot.lid.utils.Constant.getHistoryList;
import static com.dot.lid.utils.Constant.getHumanityItem;
import static com.dot.lid.utils.Constant.getLanguageList;
import static com.dot.lid.utils.Constant.getPoliticalList;
import static com.dot.lid.utils.Constant.getPoliticsItem;
import static com.dot.lid.utils.Constant.getSocietyList;
import static com.dot.lid.utils.Constant.getStateItem;
import static com.dot.lid.utils.Constant.getStateList;
import static com.dot.lid.utils.DatabaseToken.DOC_BUNDESLANDER;
import static com.dot.lid.utils.DatabaseToken.DOC_GESCHICHTE_UND_VERANTWORTUNG;
import static com.dot.lid.utils.DatabaseToken.DOC_MENSCH_UND_GESELLSCHAFT;
import static com.dot.lid.utils.DatabaseToken.DOC_POLITIK_IN_DER_DEMOKARITE;
import static com.dot.lid.utils.Language.ARABIC;
import static com.dot.lid.utils.Language.ENGLISH;
import static com.dot.lid.utils.Language.GERMAN;

public class TrainingActivity extends AppCompatActivity implements InstructionDialog.InstructionDialogInterface {

    public static int STATE_CURRENT_POSITION = 0;
    public static int POLITICAL_CURRENT_POSITION = 0;
    public static int HISTORY_CURRENT_POSITION = 0;
    public static int SOCIETY_CURRENT_POSITION = 0;
    public static int FEDERAL_CURRENT_POSITION = 0;

    private ActivityTrainingBinding binding;
    private MyApplication myApplication;
    private String currentLanguage;
    private LanguageSpinnerAdapter languageSpinnerAdapter;
    private TrainingStateSpinnerAdapter stateSpinnerAdapter;
    private PoliticalSpinnerAdapter politicalSpinnerAdapter;
    private HistorySpinnerAdapter historySpinnerAdapter;
    private SocietySpinnerAdapter societySpinnerAdapter;
    private FederalSpinnerAdapter federalSpinnerAdapter;
    private InstructionDialog instructionDialog;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrainingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar.toolbar);

        flag = getIntent().getBooleanExtra(TRAINING_ACTIVITY_KEY, false);
        getIntent().removeExtra(TRAINING_ACTIVITY_KEY);

        stateSpinnerAdapter = new TrainingStateSpinnerAdapter(this, getStateList());
        languageSpinnerAdapter = new LanguageSpinnerAdapter(this, getLanguageList());
        politicalSpinnerAdapter = new PoliticalSpinnerAdapter(this, getPoliticalList());
        historySpinnerAdapter = new HistorySpinnerAdapter(this, getHistoryList());
        societySpinnerAdapter = new SocietySpinnerAdapter(this, getSocietyList());
        federalSpinnerAdapter = new FederalSpinnerAdapter(this, getFederalList());

        binding.selectLanguageSpinner.setAdapter(languageSpinnerAdapter);

        myApplication = MyApplication.getInstance();
        currentLanguage = myApplication.getLanguage();
        if (currentLanguage.equals(ARABIC.getLanguage())) {
            updateResources(ARABIC.getLanguage());
            binding.selectLanguageSpinner.setSelection(2);
        } else if (currentLanguage.equals(GERMAN.getLanguage())) {
            updateResources(GERMAN.getLanguage());
            binding.selectLanguageSpinner.setSelection(1);
        } else {
            updateResources(ENGLISH.getLanguage());
            binding.selectLanguageSpinner.setSelection(0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.stateTrainingSpinner.setAdapter(stateSpinnerAdapter);
        binding.politicalTrainingSpinner.setAdapter(politicalSpinnerAdapter);
        binding.historyTrainingSpinner.setAdapter(historySpinnerAdapter);
        binding.societyTrainingSpinner.setAdapter(societySpinnerAdapter);
        binding.federalTrainingSpinner.setAdapter(federalSpinnerAdapter);

        binding.allQuestionsTV.setText(R.string.all_questions);
        binding.specificAreaTV.setText(R.string.specific_areas);


        binding.selectLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.selectLanguageSpinner.performClick();
            }
        });

        binding.stateTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (STATE_CURRENT_POSITION == 0) {
                    binding.stateTrainingSpinner.performClick();
                } else {
                    startTrainingActivity(DOC_BUNDESLANDER, getStateItem(STATE_CURRENT_POSITION), true);
                }

            }
        });

        binding.politicalTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (POLITICAL_CURRENT_POSITION == 0) {
                    binding.politicalTrainingSpinner.performClick();
                } else {
                    startTrainingActivity(DOC_POLITIK_IN_DER_DEMOKARITE, getPoliticsItem(POLITICAL_CURRENT_POSITION), false);
                }

            }
        });

        binding.historyTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HISTORY_CURRENT_POSITION == 0) {
                    binding.historyTrainingSpinner.performClick();
                } else {
                    startTrainingActivity(DOC_GESCHICHTE_UND_VERANTWORTUNG, getHistoryItem(HISTORY_CURRENT_POSITION), false);
                }

            }
        });

        binding.societyTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SOCIETY_CURRENT_POSITION == 0) {
                    binding.societyTrainingSpinner.performClick();
                } else {
                    startTrainingActivity(DOC_MENSCH_UND_GESELLSCHAFT, getHumanityItem(SOCIETY_CURRENT_POSITION), false);
                }

            }
        });

        binding.federalTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FEDERAL_CURRENT_POSITION == 0) {
                    binding.federalTrainingSpinner.performClick();
                } else {
                    startTrainingActivity(DOC_BUNDESLANDER, getFederalStateItem(FEDERAL_CURRENT_POSITION), false);
                }

            }
        });

        binding.selectLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (flag) {
                    flag = false;
                    return;
                } else {

                    if (i == 0) {
                        updateForSelectedLanguage(ENGLISH.getLanguage());
                    } else if (i == 1) {
                        updateForSelectedLanguage(GERMAN.getLanguage());
                    } else {
                        updateForSelectedLanguage(ARABIC.getLanguage());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.stateTrainingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                STATE_CURRENT_POSITION = i;
                if (i == 0) {
                    binding.stateTrainingIV.setImageResource(R.drawable.ic_arrow_drop_down);
                } else {
                    binding.stateTrainingIV.setImageResource(R.drawable.ic_arrow_right);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.politicalTrainingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                POLITICAL_CURRENT_POSITION = i;
                if (i == 0) {
                    binding.politicalTrainingIV.setImageResource(R.drawable.ic_arrow_drop_down);
                } else {
                    binding.politicalTrainingIV.setImageResource(R.drawable.ic_arrow_right);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.historyTrainingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HISTORY_CURRENT_POSITION = i;
                if (i == 0) {
                    binding.historyTrainingIV.setImageResource(R.drawable.ic_arrow_drop_down);
                } else {
                    binding.historyTrainingIV.setImageResource(R.drawable.ic_arrow_right);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.societyTrainingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SOCIETY_CURRENT_POSITION = i;
                if (i == 0) {
                    binding.societyTrainingIV.setImageResource(R.drawable.ic_arrow_drop_down);
                } else {
                    binding.societyTrainingIV.setImageResource(R.drawable.ic_arrow_right);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.federalTrainingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FEDERAL_CURRENT_POSITION = i;
                if (i == 0) {
                    binding.federalTrainingIV.setImageResource(R.drawable.ic_arrow_drop_down);
                } else {
                    binding.federalTrainingIV.setImageResource(R.drawable.ic_arrow_right);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.toolbar.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void startTrainingActivity(String mainCategory, String subCategory, boolean isAllQuestion) {
        Intent intent = new Intent(TrainingActivity.this, StartTrainingActivity.class);
        intent.putExtra(LANGUAGE_NAME_KEY, getCurrentLanguage(currentLanguage));
        intent.putExtra(MAIN_CATEGORY_KEY, mainCategory);
        intent.putExtra(SUB_CATEGORY_KEY, subCategory);
        intent.putExtra(ALL_QUESTION_KEY, isAllQuestion);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.training, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trainingInstruction:
                instructionDialog = new InstructionDialog();
                instructionDialog.show(getSupportFragmentManager(), "InstructionDialog");
                return true;
            case R.id.trainingAchievement:
                startActivity(new Intent(TrainingActivity.this, AchievementActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.trainingBarChart:
                startActivity(new Intent(TrainingActivity.this, BarChartActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.trainingRateApp:
                rateTheApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        invalidateOptionsMenu();
        binding.allQuestionsTV.setText(R.string.all_questions);
        binding.specificAreaTV.setText(R.string.specific_areas);
        languageSpinnerAdapter.notifyDataSetChanged();
        stateSpinnerAdapter.notifyDataSetChanged();
        politicalSpinnerAdapter.notifyDataSetChanged();
        historySpinnerAdapter.notifyDataSetChanged();
        societySpinnerAdapter.notifyDataSetChanged();
        federalSpinnerAdapter.notifyDataSetChanged();
    }

    private void updateForSelectedLanguage(String language) {
        if (!currentLanguage.equals(language)) {
            myApplication.setLanguage(language);
            currentLanguage = language;
            updateResources(currentLanguage);
            updateUI();
        }
    }

    private void updateResources(String language) {
        Locale locale = new Locale(language);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);
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


    private void rateTheApp() {
        String url = MyApplication.appLink;
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

}