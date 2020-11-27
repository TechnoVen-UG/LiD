package com.dot.lid.utils;

import android.content.res.Resources;
import android.util.Log;

import com.dot.lid.R;
import com.dot.lid.model.Achievement;
import com.dot.lid.model.Question;
import com.dot.lid.model.Selection;

import java.util.ArrayList;
import java.util.List;

import static com.dot.lid.utils.Constant.getAchievement;
import static com.dot.lid.utils.DatabaseToken.COLL_AUFGABEN_DES_STAATES;
import static com.dot.lid.utils.DatabaseToken.COLL_BADEN_WURTTEMBERG;
import static com.dot.lid.utils.DatabaseToken.COLL_BAYERN;
import static com.dot.lid.utils.DatabaseToken.COLL_BERLIN;
import static com.dot.lid.utils.DatabaseToken.COLL_BILDUNG;
import static com.dot.lid.utils.DatabaseToken.COLL_BRANDENBURG;
import static com.dot.lid.utils.DatabaseToken.COLL_BREMEN;
import static com.dot.lid.utils.DatabaseToken.COLL_DER_NATIONALSOZIALISMUS_UND_SEINE_FOLGEN;
import static com.dot.lid.utils.DatabaseToken.COLL_DEUTSCHLAND_IN_UROPA;
import static com.dot.lid.utils.DatabaseToken.COLL_FODERALISMUS;
import static com.dot.lid.utils.DatabaseToken.COLL_GRUNDRECHTE;
import static com.dot.lid.utils.DatabaseToken.COLL_HAMBURG;
import static com.dot.lid.utils.DatabaseToken.COLL_HESSEN;
import static com.dot.lid.utils.DatabaseToken.COLL_INTERKULTURELLES_ZUSAMMENLEBEN;
import static com.dot.lid.utils.DatabaseToken.COLL_KOMMUNE;
import static com.dot.lid.utils.DatabaseToken.COLL_MECKLENBURG_VORPOMMERN;
import static com.dot.lid.utils.DatabaseToken.COLL_MIGRATIONSGESCHICHTE;
import static com.dot.lid.utils.DatabaseToken.COLL_NIEDERSACHSEN;
import static com.dot.lid.utils.DatabaseToken.COLL_NORDRHEIN_WESTFALEN;
import static com.dot.lid.utils.DatabaseToken.COLL_PARTEIEN;
import static com.dot.lid.utils.DatabaseToken.COLL_PFLICHTEN;
import static com.dot.lid.utils.DatabaseToken.COLL_RECHT_UND_ALLTAG;
import static com.dot.lid.utils.DatabaseToken.COLL_RELIGIOSE_VIELFALT;
import static com.dot.lid.utils.DatabaseToken.COLL_RHEINLAND_PFALZ;
import static com.dot.lid.utils.DatabaseToken.COLL_SAARLAND;
import static com.dot.lid.utils.DatabaseToken.COLL_SACHSEN;
import static com.dot.lid.utils.DatabaseToken.COLL_SACHSEN_ANHALT;
import static com.dot.lid.utils.DatabaseToken.COLL_SCHLESWIG_HOLSTEIN;
import static com.dot.lid.utils.DatabaseToken.COLL_SOZIALSYSTEM;
import static com.dot.lid.utils.DatabaseToken.COLL_STAATSSYMBOLE;
import static com.dot.lid.utils.DatabaseToken.COLL_THURINGEN;
import static com.dot.lid.utils.DatabaseToken.COLL_VERFASSUNGSORGANE;
import static com.dot.lid.utils.DatabaseToken.COLL_VERFASSUNGSPRINZIPIEN;
import static com.dot.lid.utils.DatabaseToken.COLL_WAHLEN_UND_BETEILIGUNG;
import static com.dot.lid.utils.DatabaseToken.COLL_WICHTIGE_STATIONEN_NACH_1945;
import static com.dot.lid.utils.DatabaseToken.COLL_WIEDERVEREINIGUNG;

public class AchieveData {

    private static final String TAG = "sayed";

    public static List<Achievement> getPoliticsAchievements(Resources resources, List<Selection> selectionList) {
        float constitutional_institutions = 0;
        float constitutional_principles = 0;
        float federalism = 0;
        float social_system = 0;
        float basic_rights = 0;
        float elections_and_participation = 0;
        float political_parties = 0;
        float duties_of_the_state = 0;
        float duties = 0;
        float state_symbols = 0;
        float community = 0;
        float rights_and_everyday_life = 0;

        int constitutional_institutions_counter = 0;
        int constitutional_principles_counter = 0;
        int federalism_counter = 0;
        int social_system_counter = 0;
        int basic_rights_counter = 0;
        int elections_and_participation_counter = 0;
        int political_parties_counter = 0;
        int duties_of_the_state_counter = 0;
        int duties_counter = 0;
        int state_symbols_counter = 0;
        int community_counter = 0;
        int rights_and_everyday_life_counter = 0;


        for (Selection selection : selectionList) {
            Question question = selection.getQuestion();
            if (question.getSubCategory().equals(resources.getString(R.string.constitutional_institutions))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    constitutional_institutions++;
                }
                constitutional_institutions_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.constitutional_principles))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    constitutional_principles++;
                }
                constitutional_principles_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.federalism))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    federalism++;
                }
                federalism_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.social_system))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    social_system++;
                }
                social_system_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.basic_rights))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    basic_rights++;
                }
                basic_rights_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.elections_and_participation))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    elections_and_participation++;
                }
                elections_and_participation_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.political_parties))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    political_parties++;
                }
                political_parties_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.duties_of_the_state))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    duties_of_the_state++;
                }
                duties_of_the_state_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.duties))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    duties++;
                }
                duties_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.state_symbols))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    state_symbols++;
                }
                state_symbols_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.community))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    community++;
                }
                community_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.rights_and_everyday_life))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    rights_and_everyday_life++;
                }
                rights_and_everyday_life_counter++;
            }
        }

        List<Achievement> achievementList = new ArrayList<>();
        achievementList.add(getAchievementByCount(constitutional_institutions, constitutional_institutions_counter, COLL_VERFASSUNGSORGANE));
        achievementList.add(getAchievementByCount(constitutional_principles, constitutional_principles_counter, COLL_VERFASSUNGSPRINZIPIEN));
        achievementList.add(getAchievementByCount(federalism, federalism_counter, COLL_FODERALISMUS));
        achievementList.add(getAchievementByCount(social_system, social_system_counter, COLL_SOZIALSYSTEM));
        achievementList.add(getAchievementByCount(basic_rights, basic_rights_counter, COLL_GRUNDRECHTE));
        achievementList.add(getAchievementByCount(elections_and_participation, elections_and_participation_counter, COLL_WAHLEN_UND_BETEILIGUNG));
        achievementList.add(getAchievementByCount(political_parties, political_parties_counter, COLL_PARTEIEN));
        achievementList.add(getAchievementByCount(duties_of_the_state, duties_of_the_state_counter, COLL_AUFGABEN_DES_STAATES));
        achievementList.add(getAchievementByCount(duties, duties_counter, COLL_PFLICHTEN));
        achievementList.add(getAchievementByCount(state_symbols, state_symbols_counter, COLL_STAATSSYMBOLE));
        achievementList.add(getAchievementByCount(community, community_counter, COLL_KOMMUNE));
        achievementList.add(getAchievementByCount(rights_and_everyday_life, rights_and_everyday_life_counter, COLL_RECHT_UND_ALLTAG));
        return achievementList;
    }

    public static List<Achievement> getHistoryAchievements(Resources resources, List<Selection> selectionList) {
        float national_socialism_and_its_consequences = 0;
        float important_events_after_1945 = 0;
        float unification = 0;
        float germany_in_Europe = 0;

        int national_socialism_and_its_consequences_counter = 0;
        int important_events_after_1945_counter = 0;
        int unification_counter = 0;
        int germany_in_Europe_counter = 0;


        for (Selection selection : selectionList) {
            Question question = selection.getQuestion();
            if (question.getSubCategory().equals(resources.getString(R.string.national_socialism_and_its_consequences))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    national_socialism_and_its_consequences++;
                }
                national_socialism_and_its_consequences_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.important_events_after_1945))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    important_events_after_1945++;
                }
                important_events_after_1945_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.unification))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    unification++;
                }
                unification_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.germany_in_Europe))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    germany_in_Europe++;
                }
                germany_in_Europe_counter++;
            }
        }

        List<Achievement> achievementList = new ArrayList<>();
        achievementList.add(getAchievementByCount(national_socialism_and_its_consequences, national_socialism_and_its_consequences_counter, COLL_DER_NATIONALSOZIALISMUS_UND_SEINE_FOLGEN));
        achievementList.add(getAchievementByCount(important_events_after_1945, important_events_after_1945_counter, COLL_WICHTIGE_STATIONEN_NACH_1945));
        achievementList.add(getAchievementByCount(unification, unification_counter, COLL_WIEDERVEREINIGUNG));
        achievementList.add(getAchievementByCount(germany_in_Europe, germany_in_Europe_counter, COLL_DEUTSCHLAND_IN_UROPA));
        return achievementList;
    }

    public static List<Achievement> getHumanityAchievements(Resources resources, List<Selection> selectionList) {
        float religious_diversity = 0;
        float education = 0;
        float migration_history = 0;
        float intercultural_cohabitation = 0;

        int religious_diversity_counter = 0;
        int education_counter = 0;
        int migration_history_counter = 0;
        int intercultural_cohabitation_counter = 0;

        for (Selection selection : selectionList) {
            Question question = selection.getQuestion();
            if (question.getSubCategory().equals(resources.getString(R.string.religious_diversity))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    religious_diversity++;
                }
                religious_diversity_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.education))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    education++;
                }
                education_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.migration_history))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    migration_history++;
                }
                migration_history_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.intercultural_cohabitation))) {
                Log.d(TAG, "getHumanityAchievements: come");
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    intercultural_cohabitation++;
                }
                intercultural_cohabitation_counter++;
            }
        }

        List<Achievement> achievementList = new ArrayList<>();
        achievementList.add(getAchievementByCount(religious_diversity, religious_diversity_counter, COLL_RELIGIOSE_VIELFALT));
        achievementList.add(getAchievementByCount(education, education_counter, COLL_BILDUNG));
        achievementList.add(getAchievementByCount(migration_history, migration_history_counter, COLL_MIGRATIONSGESCHICHTE));
        achievementList.add(getAchievementByCount(intercultural_cohabitation, intercultural_cohabitation_counter, COLL_INTERKULTURELLES_ZUSAMMENLEBEN));
        return achievementList;
    }

    public static List<Achievement> getStateAchievements(Resources resources, List<Selection> selectionList) {
        float baden_württemberg = 0;
        float bavaria = 0;
        float berlin = 0;
        float brandenburg = 0;
        float bremen = 0;
        float hamburg = 0;
        float hesse = 0;
        float mecklenburg_west_pomerania = 0;
        float lower_saxony = 0;
        float northrhine_westphalia = 0;
        float rhineland_palatinate = 0;
        float saarland = 0;
        float saxony = 0;
        float saxony_anhalt = 0;
        float schleswig_holstein = 0;
        float thuringia = 0;

        int baden_württemberg_counter = 0;
        int bavaria_counter = 0;
        int berlin_counter = 0;
        int brandenburg_counter = 0;
        int bremen_counter = 0;
        int hamburg_counter = 0;
        int hesse_counter = 0;
        int mecklenburg_west_pomerania_counter = 0;
        int lower_saxony_counter = 0;
        int northrhine_westphalia_counter = 0;
        int rhineland_palatinate_counter = 0;
        int saarland_counter = 0;
        int saxony_counter = 0;
        int saxony_anhalt_counter = 0;
        int schleswig_holstein_counter = 0;
        int thuringia_counter = 0;


        for (Selection selection : selectionList) {
            Question question = selection.getQuestion();
            if (question.getSubCategory().equals(resources.getString(R.string.baden_württemberg))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    baden_württemberg++;
                }
                baden_württemberg_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.bavaria))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    bavaria++;
                }
                bavaria_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.berlin))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    berlin++;
                }
                berlin_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.brandenburg))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    brandenburg++;
                }
                brandenburg_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.bremen))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    bremen++;
                }
                bremen_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.hamburg))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    hamburg++;
                }
                hamburg_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.hesse))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    hesse++;
                }
                hesse_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.mecklenburg_west_pomerania))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    mecklenburg_west_pomerania++;
                }
                mecklenburg_west_pomerania_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.lower_saxony))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    lower_saxony++;
                }
                lower_saxony_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.northrhine_westphalia))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    northrhine_westphalia++;
                }
                northrhine_westphalia_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.rhineland_palatinate))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    rhineland_palatinate++;
                }
                rhineland_palatinate_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.saarland))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    saarland++;
                }
                saarland_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.saxony))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    saxony++;
                }
                saxony_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.saxony_anhalt))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    saxony_anhalt++;
                }
                saxony_anhalt_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.schleswig_holstein))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    schleswig_holstein++;
                }
                schleswig_holstein_counter++;
            } else if (question.getSubCategory().equals(resources.getString(R.string.thuringia))) {
                if (selection.getSelectionNumber() == question.getCorrectAnswer()) {
                    thuringia++;
                }
                thuringia_counter++;
            }
        }

        List<Achievement> achievementList = new ArrayList<>();
        achievementList.add(getAchievementByCount(baden_württemberg, baden_württemberg_counter, COLL_BADEN_WURTTEMBERG));
        achievementList.add(getAchievementByCount(bavaria, bavaria_counter, COLL_BAYERN));
        achievementList.add(getAchievementByCount(berlin, berlin_counter, COLL_BERLIN));
        achievementList.add(getAchievementByCount(brandenburg, brandenburg_counter, COLL_BRANDENBURG));
        achievementList.add(getAchievementByCount(bremen, bremen_counter, COLL_BREMEN));
        achievementList.add(getAchievementByCount(hamburg, hamburg_counter, COLL_HAMBURG));
        achievementList.add(getAchievementByCount(hesse, hesse_counter, COLL_HESSEN));
        achievementList.add(getAchievementByCount(mecklenburg_west_pomerania, mecklenburg_west_pomerania_counter, COLL_MECKLENBURG_VORPOMMERN));
        achievementList.add(getAchievementByCount(lower_saxony, lower_saxony_counter, COLL_NIEDERSACHSEN));
        achievementList.add(getAchievementByCount(northrhine_westphalia, northrhine_westphalia_counter, COLL_NORDRHEIN_WESTFALEN));
        achievementList.add(getAchievementByCount(rhineland_palatinate, rhineland_palatinate_counter, COLL_RHEINLAND_PFALZ));
        achievementList.add(getAchievementByCount(saarland, saarland_counter, COLL_SAARLAND));
        achievementList.add(getAchievementByCount(saxony, saxony_counter, COLL_SACHSEN));
        achievementList.add(getAchievementByCount(saxony_anhalt, saxony_anhalt_counter, COLL_SACHSEN_ANHALT));
        achievementList.add(getAchievementByCount(schleswig_holstein, schleswig_holstein_counter, COLL_SCHLESWIG_HOLSTEIN));
        achievementList.add(getAchievementByCount(thuringia, thuringia_counter, COLL_THURINGEN));
        return achievementList;
    }

    private static Achievement getAchievementByCount(float score, int count, String subCategory) {
        float percentage = 0;
        if (score != 0) {
            percentage = (score / count) * 100;
        }
        Log.d(TAG, "getAchievementByCount: " + subCategory + "  " + count + "  " + score + "  " + percentage);
        Achievement achievement;
        achievement = getAchievement(subCategory);
        if (percentage >= 50 && percentage < 70) {
            achievement.setHasBronze(true);
        } else if (percentage >= 70 && percentage < 90) {
            achievement.setHasSilver(true);
        } else if (percentage >= 90 && percentage < 100) {
            achievement.setHasGold(true);
        } else if (percentage >= 100) {
            achievement.setHasPlatinum(true);
        }
        return achievement;
    }
}
