package com.dot.lid.utils;

import com.dot.lid.R;
import com.dot.lid.model.Achievement;
import com.dot.lid.model.State;

import java.util.ArrayList;
import java.util.List;

import static com.dot.lid.utils.DatabaseToken.COLLECTION_ARABIC;
import static com.dot.lid.utils.DatabaseToken.COLLECTION_ENGLISH;
import static com.dot.lid.utils.DatabaseToken.COLLECTION_GERMAN;
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
import static com.dot.lid.utils.DatabaseToken.COLL_GENERAL_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.COLL_GRUNDRECHTE;
import static com.dot.lid.utils.DatabaseToken.COLL_HAMBURG;
import static com.dot.lid.utils.DatabaseToken.COLL_HESSEN;
import static com.dot.lid.utils.DatabaseToken.COLL_HISTORY_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.COLL_HUMANITY_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.COLL_INTERKULTURELLES_ZUSAMMENLEBEN;
import static com.dot.lid.utils.DatabaseToken.COLL_KOMMUNE;
import static com.dot.lid.utils.DatabaseToken.COLL_MECKLENBURG_VORPOMMERN;
import static com.dot.lid.utils.DatabaseToken.COLL_MIGRATIONSGESCHICHTE;
import static com.dot.lid.utils.DatabaseToken.COLL_NIEDERSACHSEN;
import static com.dot.lid.utils.DatabaseToken.COLL_NORDRHEIN_WESTFALEN;
import static com.dot.lid.utils.DatabaseToken.COLL_PARTEIEN;
import static com.dot.lid.utils.DatabaseToken.COLL_PFLICHTEN;
import static com.dot.lid.utils.DatabaseToken.COLL_POLITICS_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.COLL_RECHT_UND_ALLTAG;
import static com.dot.lid.utils.DatabaseToken.COLL_RELIGIOSE_VIELFALT;
import static com.dot.lid.utils.DatabaseToken.COLL_RHEINLAND_PFALZ;
import static com.dot.lid.utils.DatabaseToken.COLL_SAARLAND;
import static com.dot.lid.utils.DatabaseToken.COLL_SACHSEN;
import static com.dot.lid.utils.DatabaseToken.COLL_SACHSEN_ANHALT;
import static com.dot.lid.utils.DatabaseToken.COLL_SCHLESWIG_HOLSTEIN;
import static com.dot.lid.utils.DatabaseToken.COLL_SOZIALSYSTEM;
import static com.dot.lid.utils.DatabaseToken.COLL_STAATSSYMBOLE;
import static com.dot.lid.utils.DatabaseToken.COLL_STATE_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.COLL_THURINGEN;
import static com.dot.lid.utils.DatabaseToken.COLL_VERFASSUNGSORGANE;
import static com.dot.lid.utils.DatabaseToken.COLL_VERFASSUNGSPRINZIPIEN;
import static com.dot.lid.utils.DatabaseToken.COLL_WAHLEN_UND_BETEILIGUNG;
import static com.dot.lid.utils.DatabaseToken.COLL_WICHTIGE_STATIONEN_NACH_1945;
import static com.dot.lid.utils.DatabaseToken.COLL_WIEDERVEREINIGUNG;
import static com.dot.lid.utils.Language.ARABIC;
import static com.dot.lid.utils.Language.GERMAN;

public class Constant {
    public static final String CURRENT_LANGUAGE_CODE_KEY = "language";
    public static final String OPTION_PREFERENCE_KEY = "option_preference";
    public static final String LANGUAGE_PREFERENCE_KEY = "language_preference";
    public static final String LANGUAGE__KEY = "language";
    public static final String SOUND_STATE = "sound_sate";
    public static final String SHOW_ANSWER_STATE = "show_answer_state";
    public static final String TRAINING_ACTIVITY_KEY = "training_key";
    public static final String TEST_ACTIVITY_KEY = "test_key";
    public static final String MAIN_CATEGORY_KEY = "main_category_name";
    public static final String SUB_CATEGORY_KEY = "sub_category_name";
    public static final String LANGUAGE_NAME_KEY = "language_name";
    public static final String ALL_QUESTION_KEY = "all_question";

    public static ArrayList<State> getStateList() {
        ArrayList<State> stateList = new ArrayList<>();
        stateList.add(new State(R.string.select_state, 0));
        stateList.add(new State(R.string.baden_württemberg, R.drawable.ic_flag_of_baden_wurttemberg));
        stateList.add(new State(R.string.bavaria, R.drawable.ic_flag_of_bavaria));
        stateList.add(new State(R.string.berlin, R.drawable.ic_flag_of_berlin));
        stateList.add(new State(R.string.brandenburg, R.drawable.ic_flag_of_brandenburg));
        stateList.add(new State(R.string.bremen, R.drawable.ic_flag_of_bremen));
        stateList.add(new State(R.string.hamburg, R.drawable.ic_flag_of_hamburg));
        stateList.add(new State(R.string.hesse, R.drawable.ic_flag_of_hesse));
        stateList.add(new State(R.string.mecklenburg_west_pomerania, R.drawable.ic_flag_of_mecklenburg_western_pomerania));
        stateList.add(new State(R.string.lower_saxony, R.drawable.ic_flag_of_lower_saxony));
        stateList.add(new State(R.string.northrhine_westphalia, R.drawable.ic_flag_of_north_rhine_westphalia));
        stateList.add(new State(R.string.rhineland_palatinate, R.drawable.ic_flag_of_rhineland_palatinate));
        stateList.add(new State(R.string.saarland, R.drawable.ic_flag_of_saarland));
        stateList.add(new State(R.string.saxony, R.drawable.ic_flag_of_saxony));
        stateList.add(new State(R.string.saxony_anhalt, R.drawable.ic_flag_of_saxony_anhalt));
        stateList.add(new State(R.string.schleswig_holstein, R.drawable.ic_flag_of_schleswig_holstein));
        stateList.add(new State(R.string.thuringia, R.drawable.ic_flag_of_thuringia));

        return stateList;
    }

    public static List<Achievement> getAchievementList() {
        List<Achievement> achievementList = new ArrayList<>();

        achievementList.add(new Achievement(COLL_VERFASSUNGSORGANE, R.string.politics_in_the_democracy,
                R.string.constitutional_institutions, false, false, false, false));

        achievementList.add(new Achievement(COLL_VERFASSUNGSPRINZIPIEN, R.string.politics_in_the_democracy, R.string.constitutional_principles,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_FODERALISMUS, R.string.politics_in_the_democracy, R.string.federalism,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_SOZIALSYSTEM, R.string.politics_in_the_democracy, R.string.social_system,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_GRUNDRECHTE, R.string.politics_in_the_democracy, R.string.basic_rights,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_WAHLEN_UND_BETEILIGUNG, R.string.politics_in_the_democracy, R.string.elections_and_participation,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_PARTEIEN, R.string.politics_in_the_democracy, R.string.political_parties,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_AUFGABEN_DES_STAATES, R.string.politics_in_the_democracy, R.string.duties_of_the_state,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_PFLICHTEN, R.string.politics_in_the_democracy, R.string.duties,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_STAATSSYMBOLE, R.string.politics_in_the_democracy, R.string.state_symbols,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_KOMMUNE, R.string.politics_in_the_democracy, R.string.community,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_RECHT_UND_ALLTAG, R.string.politics_in_the_democracy, R.string.rights_and_everyday_life,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_DER_NATIONALSOZIALISMUS_UND_SEINE_FOLGEN, R.string.history_and_responsibility,
                R.string.national_socialism_and_its_consequences, false, false, false, false));

        achievementList.add(new Achievement(COLL_WICHTIGE_STATIONEN_NACH_1945, R.string.history_and_responsibility,
                R.string.important_events_after_1945, false, false, false, false));

        achievementList.add(new Achievement(COLL_WIEDERVEREINIGUNG, R.string.history_and_responsibility, R.string.unification,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_DEUTSCHLAND_IN_UROPA, R.string.history_and_responsibility, R.string.germany_in_Europe,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_RELIGIOSE_VIELFALT, R.string.humanity_and_society, R.string.religious_diversity,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_BILDUNG, R.string.humanity_and_society, R.string.education,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_MIGRATIONSGESCHICHTE, R.string.humanity_and_society, R.string.migration_history,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_INTERKULTURELLES_ZUSAMMENLEBEN, R.string.humanity_and_society, R.string.intercultural_cohabitation,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_BADEN_WURTTEMBERG, R.string.federal_states, R.string.baden_württemberg,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_BAYERN, R.string.federal_states, R.string.bavaria,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_BERLIN, R.string.federal_states, R.string.berlin,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_BRANDENBURG, R.string.federal_states, R.string.brandenburg,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_BREMEN, R.string.federal_states, R.string.bremen,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_HAMBURG, R.string.federal_states, R.string.hamburg,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_HESSEN, R.string.federal_states, R.string.hesse,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_MECKLENBURG_VORPOMMERN, R.string.federal_states, R.string.mecklenburg_west_pomerania,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_NIEDERSACHSEN, R.string.federal_states, R.string.lower_saxony,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_NORDRHEIN_WESTFALEN, R.string.federal_states, R.string.northrhine_westphalia,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_RHEINLAND_PFALZ, R.string.federal_states, R.string.rhineland_palatinate,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_SAARLAND, R.string.federal_states, R.string.saarland,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_SACHSEN, R.string.federal_states, R.string.saxony,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_SACHSEN_ANHALT, R.string.federal_states, R.string.saxony_anhalt,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_SCHLESWIG_HOLSTEIN, R.string.federal_states, R.string.schleswig_holstein,
                false, false, false, false));

        achievementList.add(new Achievement(COLL_THURINGEN, R.string.federal_states, R.string.thuringia,
                false, false, false, false));

        return achievementList;
    }

    public static Achievement getAchievement(String category) {
        switch (category) {

            case COLL_POLITICS_ALL_QUESTION:
                return new Achievement(COLL_POLITICS_ALL_QUESTION, R.string.politics_in_the_democracy, R.string.all_questions,
                        false, false, false, false);
            case COLL_VERFASSUNGSORGANE:
                return new Achievement(COLL_VERFASSUNGSORGANE, R.string.politics_in_the_democracy, R.string.constitutional_institutions,
                        false, false, false, false);
            case COLL_VERFASSUNGSPRINZIPIEN:
                return new Achievement(COLL_VERFASSUNGSPRINZIPIEN, R.string.politics_in_the_democracy, R.string.constitutional_principles,
                        false, false, false, false);
            case COLL_FODERALISMUS:
                return new Achievement(COLL_FODERALISMUS, R.string.politics_in_the_democracy, R.string.federalism,
                        false, false, false, false);

            case COLL_SOZIALSYSTEM:
                return new Achievement(COLL_SOZIALSYSTEM, R.string.politics_in_the_democracy, R.string.social_system,
                        false, false, false, false);

            case COLL_GRUNDRECHTE:
                return new Achievement(COLL_GRUNDRECHTE, R.string.politics_in_the_democracy, R.string.basic_rights,
                        false, false, false, false);

            case COLL_WAHLEN_UND_BETEILIGUNG:
                return new Achievement(COLL_WAHLEN_UND_BETEILIGUNG, R.string.politics_in_the_democracy, R.string.elections_and_participation,
                        false, false, false, false);

            case COLL_PARTEIEN:
                return new Achievement(COLL_PARTEIEN, R.string.politics_in_the_democracy, R.string.political_parties,
                        false, false, false, false);

            case COLL_AUFGABEN_DES_STAATES:
                return new Achievement(COLL_AUFGABEN_DES_STAATES, R.string.politics_in_the_democracy, R.string.duties_of_the_state,
                        false, false, false, false);

            case COLL_PFLICHTEN:
                return new Achievement(COLL_PFLICHTEN, R.string.politics_in_the_democracy, R.string.duties,
                        false, false, false, false);

            case COLL_STAATSSYMBOLE:
                return new Achievement(COLL_STAATSSYMBOLE, R.string.politics_in_the_democracy, R.string.state_symbols,
                        false, false, false, false);

            case COLL_KOMMUNE:
                return new Achievement(COLL_KOMMUNE, R.string.politics_in_the_democracy, R.string.community,
                        false, false, false, false);

            case COLL_RECHT_UND_ALLTAG:
                return new Achievement(COLL_RECHT_UND_ALLTAG, R.string.politics_in_the_democracy, R.string.rights_and_everyday_life,
                        false, false, false, false);

            case COLL_HISTORY_ALL_QUESTION:
                return new Achievement(COLL_HISTORY_ALL_QUESTION, R.string.history_and_responsibility, R.string.all_questions,
                        false, false, false, false);

            case COLL_DER_NATIONALSOZIALISMUS_UND_SEINE_FOLGEN:
                return new Achievement(COLL_DER_NATIONALSOZIALISMUS_UND_SEINE_FOLGEN, R.string.history_and_responsibility,
                        R.string.national_socialism_and_its_consequences, false, false, false, false);

            case COLL_WICHTIGE_STATIONEN_NACH_1945:
                return new Achievement(COLL_WICHTIGE_STATIONEN_NACH_1945, R.string.history_and_responsibility,
                        R.string.important_events_after_1945, false, false, false, false);

            case COLL_WIEDERVEREINIGUNG:
                return new Achievement(COLL_WIEDERVEREINIGUNG, R.string.history_and_responsibility, R.string.unification,
                        false, false, false, false);

            case COLL_DEUTSCHLAND_IN_UROPA:
                return new Achievement(COLL_DEUTSCHLAND_IN_UROPA, R.string.history_and_responsibility, R.string.germany_in_Europe,
                        false, false, false, false);

            case COLL_HUMANITY_ALL_QUESTION:
                return new Achievement(COLL_HUMANITY_ALL_QUESTION, R.string.humanity_and_society, R.string.all_questions,
                        false, false, false, false);

            case COLL_RELIGIOSE_VIELFALT:
                return new Achievement(COLL_RELIGIOSE_VIELFALT, R.string.humanity_and_society, R.string.religious_diversity,
                        false, false, false, false);

            case COLL_BILDUNG:
                return new Achievement(COLL_BILDUNG, R.string.humanity_and_society, R.string.education,
                        false, false, false, false);

            case COLL_MIGRATIONSGESCHICHTE:
                return new Achievement(COLL_MIGRATIONSGESCHICHTE, R.string.humanity_and_society, R.string.migration_history,
                        false, false, false, false);

            case COLL_INTERKULTURELLES_ZUSAMMENLEBEN:
                return new Achievement(COLL_INTERKULTURELLES_ZUSAMMENLEBEN, R.string.humanity_and_society, R.string.intercultural_cohabitation,
                        false, false, false, false);

            case COLL_STATE_ALL_QUESTION:
                return new Achievement(COLL_STATE_ALL_QUESTION, R.string.federal_states, R.string.all_questions,
                        false, false, false, false);

            case COLL_BADEN_WURTTEMBERG:
                return new Achievement(COLL_BADEN_WURTTEMBERG, R.string.federal_states, R.string.baden_württemberg,
                        false, false, false, false);

            case COLL_BAYERN:
                return new Achievement(COLL_BAYERN, R.string.federal_states, R.string.bavaria,
                        false, false, false, false);

            case COLL_BERLIN:
                return new Achievement(COLL_BERLIN, R.string.federal_states, R.string.berlin,
                        false, false, false, false);

            case COLL_BRANDENBURG:
                return new Achievement(COLL_BRANDENBURG, R.string.federal_states, R.string.brandenburg,
                        false, false, false, false);

            case COLL_BREMEN:
                return new Achievement(COLL_BREMEN, R.string.federal_states, R.string.bremen,
                        false, false, false, false);

            case COLL_HAMBURG:
                return new Achievement(COLL_HAMBURG, R.string.federal_states, R.string.hamburg,
                        false, false, false, false);

            case COLL_HESSEN:
                return new Achievement(COLL_HESSEN, R.string.federal_states, R.string.hesse,
                        false, false, false, false);

            case COLL_MECKLENBURG_VORPOMMERN:
                return new Achievement(COLL_MECKLENBURG_VORPOMMERN, R.string.federal_states, R.string.mecklenburg_west_pomerania,
                        false, false, false, false);

            case COLL_NIEDERSACHSEN:
                return new Achievement(COLL_NIEDERSACHSEN, R.string.federal_states, R.string.lower_saxony,
                        false, false, false, false);

            case COLL_NORDRHEIN_WESTFALEN:
                return new Achievement(COLL_NORDRHEIN_WESTFALEN, R.string.federal_states, R.string.northrhine_westphalia,
                        false, false, false, false);

            case COLL_RHEINLAND_PFALZ:
                return new Achievement(COLL_RHEINLAND_PFALZ, R.string.federal_states, R.string.rhineland_palatinate,
                        false, false, false, false);

            case COLL_SAARLAND:
                return new Achievement(COLL_SAARLAND, R.string.federal_states, R.string.saarland,
                        false, false, false, false);

            case COLL_SACHSEN:
                return new Achievement(COLL_SACHSEN, R.string.federal_states, R.string.saxony,
                        false, false, false, false);

            case COLL_SACHSEN_ANHALT:
                return new Achievement(COLL_SACHSEN_ANHALT, R.string.federal_states, R.string.saxony_anhalt,
                        false, false, false, false);

            case COLL_SCHLESWIG_HOLSTEIN:
                return new Achievement(COLL_SCHLESWIG_HOLSTEIN, R.string.federal_states, R.string.schleswig_holstein,
                        false, false, false, false);

            case COLL_THURINGEN:
                return new Achievement(COLL_THURINGEN, R.string.federal_states, R.string.thuringia,
                        false, false, false, false);

            case COLL_GENERAL_ALL_QUESTION:
                return new Achievement(COLL_GENERAL_ALL_QUESTION, R.string.general_practice, R.string.all_questions,
                        false, false, false, false);

            default:
                return null;
        }
    }

    public static Integer[] getLanguageList() {
        return new Integer[]{
                R.string.english,
                R.string.german,
                R.string.arabic
        };
    }

    public static Integer[] getPoliticalList() {
        return new Integer[]{
                R.string.politics_in_the_democracy,
                R.string.all_questions,
                R.string.constitutional_institutions,
                R.string.constitutional_principles,
                R.string.federalism,
                R.string.social_system,
                R.string.basic_rights,
                R.string.elections_and_participation,
                R.string.political_parties,
                R.string.duties_of_the_state,
                R.string.duties,
                R.string.state_symbols,
                R.string.community,
                R.string.rights_and_everyday_life
        };
    }

    public static Integer[] getHistoryList() {
        return new Integer[]{
                R.string.history_and_responsibility,
                R.string.all_questions,
                R.string.national_socialism_and_its_consequences,
                R.string.important_events_after_1945,
                R.string.unification,
                R.string.germany_in_Europe,
        };
    }

    public static Integer[] getSocietyList() {
        return new Integer[]{
                R.string.humanity_and_society,
                R.string.all_questions,
                R.string.religious_diversity,
                R.string.education,
                R.string.migration_history,
                R.string.intercultural_cohabitation,
        };
    }

    public static Integer[] getFederalList() {
        return new Integer[]{
                R.string.federal_states,
                R.string.all_questions,
                R.string.baden_württemberg,
                R.string.bavaria,
                R.string.berlin,
                R.string.brandenburg,
                R.string.bremen,
                R.string.hamburg,
                R.string.hesse,
                R.string.mecklenburg_west_pomerania,
                R.string.lower_saxony,
                R.string.northrhine_westphalia,
                R.string.rhineland_palatinate,
                R.string.saarland,
                R.string.saxony,
                R.string.saxony_anhalt,
                R.string.schleswig_holstein,
                R.string.thuringia,
        };
    }

    public static String getFederalStateItem(int position) {
        switch (position) {
            case 1:
                return COLL_STATE_ALL_QUESTION;
            case 2:
                return COLL_BADEN_WURTTEMBERG;
            case 3:
                return COLL_BAYERN;
            case 4:
                return COLL_BERLIN;
            case 5:
                return COLL_BRANDENBURG;
            case 6:
                return COLL_BREMEN;
            case 7:
                return COLL_HAMBURG;
            case 8:
                return COLL_HESSEN;
            case 9:
                return COLL_MECKLENBURG_VORPOMMERN;
            case 10:
                return COLL_NIEDERSACHSEN;
            case 11:
                return COLL_NORDRHEIN_WESTFALEN;
            case 12:
                return COLL_RHEINLAND_PFALZ;
            case 13:
                return COLL_SAARLAND;
            case 14:
                return COLL_SACHSEN;
            case 15:
                return COLL_SACHSEN_ANHALT;
            case 16:
                return COLL_SCHLESWIG_HOLSTEIN;
            case 17:
                return COLL_THURINGEN;
            default:
                return null;
        }
    }

    public static String getStateItem(int position) {
        switch (position) {
            case 1:
                return COLL_BADEN_WURTTEMBERG;
            case 2:
                return COLL_BAYERN;
            case 3:
                return COLL_BERLIN;
            case 4:
                return COLL_BRANDENBURG;
            case 5:
                return COLL_BREMEN;
            case 6:
                return COLL_HAMBURG;
            case 7:
                return COLL_HESSEN;
            case 8:
                return COLL_MECKLENBURG_VORPOMMERN;
            case 9:
                return COLL_NIEDERSACHSEN;
            case 10:
                return COLL_NORDRHEIN_WESTFALEN;
            case 11:
                return COLL_RHEINLAND_PFALZ;
            case 12:
                return COLL_SAARLAND;
            case 13:
                return COLL_SACHSEN;
            case 14:
                return COLL_SACHSEN_ANHALT;
            case 15:
                return COLL_SCHLESWIG_HOLSTEIN;
            case 16:
                return COLL_THURINGEN;
            default:
                return null;
        }
    }

    public static String getHumanityItem(int position) {
        switch (position) {
            case 1:
                return COLL_HUMANITY_ALL_QUESTION;
            case 2:
                return COLL_RELIGIOSE_VIELFALT;
            case 3:
                return COLL_BILDUNG;
            case 4:
                return COLL_MIGRATIONSGESCHICHTE;
            case 5:
                return COLL_INTERKULTURELLES_ZUSAMMENLEBEN;
            default:
                return null;
        }
    }

    public static String getHistoryItem(int position) {
        switch (position) {
            case 1:
                return COLL_HISTORY_ALL_QUESTION;
            case 2:
                return COLL_DER_NATIONALSOZIALISMUS_UND_SEINE_FOLGEN;
            case 3:
                return COLL_WICHTIGE_STATIONEN_NACH_1945;
            case 4:
                return COLL_WIEDERVEREINIGUNG;
            case 5:
                return COLL_DEUTSCHLAND_IN_UROPA;
            default:
                return null;
        }
    }

    public static String getPoliticsItem(int position) {
        switch (position) {
            case 1:
                return COLL_POLITICS_ALL_QUESTION;
            case 2:
                return COLL_VERFASSUNGSORGANE;
            case 3:
                return COLL_VERFASSUNGSPRINZIPIEN;
            case 4:
                return COLL_FODERALISMUS;
            case 5:
                return COLL_SOZIALSYSTEM;
            case 6:
                return COLL_GRUNDRECHTE;
            case 7:
                return COLL_WAHLEN_UND_BETEILIGUNG;
            case 8:
                return COLL_PARTEIEN;
            case 9:
                return COLL_AUFGABEN_DES_STAATES;
            case 10:
                return COLL_PFLICHTEN;
            case 11:
                return COLL_STAATSSYMBOLE;
            case 12:
                return COLL_KOMMUNE;
            case 13:
                return COLL_RECHT_UND_ALLTAG;
            default:
                return null;
        }
    }

    public static String getCurrentLanguage(String currentLanguage) {
        if (currentLanguage.equals(ARABIC.getLanguage())) {
            return COLLECTION_ARABIC;
        } else if (currentLanguage.equals(GERMAN.getLanguage())) {
            return COLLECTION_GERMAN;
        } else {
            return COLLECTION_ENGLISH;
        }
    }
}
