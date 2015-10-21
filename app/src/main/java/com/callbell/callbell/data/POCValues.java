package com.callbell.callbell.data;

import android.content.ComponentName;
import android.content.Context;
import android.widget.ListAdapter;

import com.callbell.callbell.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class POCValues {

    public static Map<String, List<String>> pocMap;
    public static Map<String, String> testDescriptions;
    private Context mContext;

    public static String DEFAULT_CHOICE = "Select Chief Complaint...";

    //TESTS
    public final String ABDOMINAL_PAIN;
    public final String URINALYSIS;
    public final String IV;
    public final String PAIN_MEDICATION;
    public final String NAUSEA_MEDICATION;
    public final String CT_SCAN;
    public final String ULTRASOUND;
    public final String X_RAY;
    public final String UROLOGY_CONSULT;
    public final String HEADACHE;
    public final String MEDICAL_EVALUATION;
    public final String CBC;
    public final String CRP;
    public final String BMP;
    public final String INR;
    public final String LACTIC_ACID;
    public final String IV_THERAPY;
    public final String IV_FLUID;
    public final String MRI;
    public final String NEUROSURGERY_CONSULT;
    public final String NEUROLOGY_CONSULT;
    public final String LEG_PAIN;
    public final String CMP;
    public final String ESR;
    public final String D_DIMER;
    public final String C_REACTIVE_PROTEIN;
    public final String URIC_ACID;
    public final String ULTRASOUND__;
    public final String PREGNANCY_PROBLEMS;
    public final String HCG;
    public final String PELVIC_EXAM;
    public final String OBGYN_CONSULT;
    public final String RECTAL_BLEEDING;
    public final String TYPE_SCREEN;
    public final String BLOOD_TRANSFUSION;
    public final String CHEST_X_RAY;
    public final String SHORTNESS_OF_BREATH;
    public final String EKG;
    public final String OXYGEN;
    public final String TROPONIN;
    public final String BNP;
    public final String BLOOD_GAS;
    public final String IV_PLACEMENT;
    public final String ECHO;
    public final String NEBULIZER_TREATMENT;
    public final String PEAK_FLOW_METER;
    public final String INPATIENT_CONSULT;
    public final String SLURRED_SPEAK;
    public final String AMMONIA;
    public final String FINGER_STICK;
    public final String SWALLOW_SCREEN;
    public final String TESTICULAR_PAIN;
    public final String FOLEY_CATHETER;
    public final String VAGINAL_BLEEDING;
    public final String ALTERED_MENTAL_STATUS;
    public final String ETHANOL;
    public final String LIPASE;
    public final String AMYLASE;
    public final String FLU_SCREENING;
    public final String CAT_SCAN;
    public final String VOMITING_BLOOD;
    public final String ANTACID_MEDICATION;
    public final String GI_CONSULT;
    public final String NAUSEA_VOMITING_DIARRHEA;
    public final String MEDICAL_EVALUATION_INR;
    public final String STOOL_CULTURE;
    public final String LFT;
    public final String LACERATION;
    public final String TETANUS_SHOT;
    public final String STITCHES;
    public final String EYE_INJURY;
    public final String EYE_DROPS;
    public final String EYE_IRRIGATION;
    public final String ANTIBIOTICS;
    public final String LEG_SWELLING;
    public final String FACIAL_SWELLING;
    public final String ALLERGIC_REACTION;
    public final String MONITOR;
    public final String PALPITATIONS;
    public final String ECHOCARDIOGRAM;
    public final String PT;
    public final String PTT_INR;
    public final String REPEAT_TROPONIN;
    public final String STRESS_TEST;
    public final String HOLTER_MONITOR;
    public final String CARDIOLOGY_CONSULT;
    public final String RASH;
    public final String IV_INSERTION;
    public final String OVERDOSE;
    public final String ACETAMINOPHEN_LEVEL;
    public final String ASPIRIN_LEVEL;
    public final String SOCIAL_WORK_CONSULT;
    public final String ABNORMAL_LABS;
    public final String TSH;
    public final String ARM_PAIN;
    public final String ASTHMA;
    public final String BACK_PAIN;
    public final String BLOOD_IN_URINE;
    public final String FLANK_PAIN;
    public final String CHEST_PAIN;


    public static String OTHER_CHOICE = "Other";

    //TODO: array values should be constants defined staticly that way we can reference better track these things
    @Inject
    public POCValues(Context context) {
        pocMap = new LinkedHashMap<>();
        mContext = context;

        //TESTS
        ABDOMINAL_PAIN = mContext.getString(R.string.abdominal_pain);
        URINALYSIS = mContext.getString(R.string.urinalysis);
        IV = mContext.getString(R.string.iv);
        PAIN_MEDICATION = mContext.getString(R.string.pain_medication);
        NAUSEA_MEDICATION = mContext.getString(R.string.nausea_medication);
        CT_SCAN = mContext.getString(R.string.ct_scan);
        ULTRASOUND = mContext.getString(R.string.ultrasound);
        X_RAY = mContext.getString(R.string.x_ray);
        UROLOGY_CONSULT = mContext.getString(R.string.urology_consult);
        HEADACHE = mContext.getString(R.string.headache);
        MEDICAL_EVALUATION = mContext.getString(R.string.medical_evaluation);
        CBC = mContext.getString(R.string.cbc);
        CRP = mContext.getString(R.string.crp);
        BMP = mContext.getString(R.string.bmp);
        INR = mContext.getString(R.string.inr);
        LACTIC_ACID = mContext.getString(R.string.lactic_acid);
        IV_THERAPY = mContext.getString(R.string.iv_therapy);
        IV_FLUID = mContext.getString(R.string.iv_fluid);
        MRI = mContext.getString(R.string.mri);
        NEUROSURGERY_CONSULT = mContext.getString(R.string.neurosurgery_consult);
        NEUROLOGY_CONSULT = mContext.getString(R.string.neurology_consult);
        LEG_PAIN = mContext.getString(R.string.leg_pain);
        CMP = mContext.getString(R.string.cmp);
        ESR = mContext.getString(R.string.esr);
        D_DIMER = mContext.getString(R.string.d_dimer);
        C_REACTIVE_PROTEIN = mContext.getString(R.string.c_reactive_protein);
        URIC_ACID = mContext.getString(R.string.uric_acid);
        ULTRASOUND__ = mContext.getString(R.string.ultrasound__);
        PREGNANCY_PROBLEMS = mContext.getString(R.string.pregnancy_problems);
        HCG = mContext.getString(R.string.hcg);
        PELVIC_EXAM = mContext.getString(R.string.pelvic_exam);
        OBGYN_CONSULT = mContext.getString(R.string.obgyn_consult);
        RECTAL_BLEEDING = mContext.getString(R.string.rectal_bleeding);
        TYPE_SCREEN = mContext.getString(R.string.type_screen);
        BLOOD_TRANSFUSION = mContext.getString(R.string.blood_transfusion);
        CHEST_X_RAY = mContext.getString(R.string.chest_x_ray);
        SHORTNESS_OF_BREATH = mContext.getString(R.string.shortness_of_breath);
        EKG = mContext.getString(R.string.ekg);
        OXYGEN = mContext.getString(R.string.oxygen);
        TROPONIN = mContext.getString(R.string.troponin);
        BNP = mContext.getString(R.string.bnp);
        BLOOD_GAS = mContext.getString(R.string.blood_gas);
        IV_PLACEMENT = mContext.getString(R.string.iv_placement);
        ECHO = mContext.getString(R.string.echo);
        NEBULIZER_TREATMENT = mContext.getString(R.string.nebulizer_treatment);
        PEAK_FLOW_METER = mContext.getString(R.string.peak_flow_meter);
        INPATIENT_CONSULT = mContext.getString(R.string.inpatient_consult);
        SLURRED_SPEAK = mContext.getString(R.string.slurred_speak);
        AMMONIA = mContext.getString(R.string.ammonia);
        FINGER_STICK = mContext.getString(R.string.finger_stick);
        SWALLOW_SCREEN = mContext.getString(R.string.swallow_screen);
        TESTICULAR_PAIN = mContext.getString(R.string.testicular_pain);
        FOLEY_CATHETER = mContext.getString(R.string.foley_catheter);
        VAGINAL_BLEEDING = mContext.getString(R.string.vaginal_bleeding);
        ALTERED_MENTAL_STATUS = mContext.getString(R.string.altered_mental_status);
        ETHANOL = mContext.getString(R.string.ethanol);
        LIPASE = mContext.getString(R.string.lipase);
        AMYLASE = mContext.getString(R.string.amylase);
        FLU_SCREENING = mContext.getString(R.string.flu_screening);
        CAT_SCAN = mContext.getString(R.string.cat_scan);
        VOMITING_BLOOD = mContext.getString(R.string.vomiting_blood);
        ANTACID_MEDICATION = mContext.getString(R.string.antacid_medication);
        GI_CONSULT = mContext.getString(R.string.gi_consult);
        NAUSEA_VOMITING_DIARRHEA = mContext.getString(R.string.nausea_vomiting_diarrhea);
        MEDICAL_EVALUATION_INR = mContext.getString(R.string.medical_evaluation_inr);
        STOOL_CULTURE = mContext.getString(R.string.stool_culture);
        LFT = mContext.getString(R.string.lft);
        LACERATION = mContext.getString(R.string.laceration);
        TETANUS_SHOT = mContext.getString(R.string.tetanus_shot);
        STITCHES = mContext.getString(R.string.stitches);
        EYE_INJURY = mContext.getString(R.string.eye_injury);
        EYE_DROPS = mContext.getString(R.string.eye_drops);
        EYE_IRRIGATION = mContext.getString(R.string.eye_irrigation);
        ANTIBIOTICS = mContext.getString(R.string.antibiotics);
        LEG_SWELLING = mContext.getString(R.string.leg_swelling);
        FACIAL_SWELLING = mContext.getString(R.string.facial_swelling);
        ALLERGIC_REACTION = mContext.getString(R.string.allergic_reaction);
        MONITOR = mContext.getString(R.string.monitor);
        PALPITATIONS = mContext.getString(R.string.palpitations);
        ECHOCARDIOGRAM = mContext.getString(R.string.echocardiogram);
        PT = mContext.getString(R.string.pt);
        PTT_INR = mContext.getString(R.string.ptt_inr);
        REPEAT_TROPONIN = mContext.getString(R.string.repeat_troponin);
        STRESS_TEST = mContext.getString(R.string.stress_test);
        HOLTER_MONITOR = mContext.getString(R.string.holter_monitor);
        CARDIOLOGY_CONSULT = mContext.getString(R.string.cardiology_consult);
        RASH = mContext.getString(R.string.rash);
        IV_INSERTION = mContext.getString(R.string.iv_insertion);
        OVERDOSE = mContext.getString(R.string.overdose);
        ACETAMINOPHEN_LEVEL = mContext.getString(R.string.acetaminophen_level);
        ASPIRIN_LEVEL = mContext.getString(R.string.aspirin_level);
        SOCIAL_WORK_CONSULT = mContext.getString(R.string.social_work_consult);
        ABNORMAL_LABS = mContext.getString(R.string.abnormal_labs);
        TSH = mContext.getString(R.string.tsh);
        ARM_PAIN = mContext.getString(R.string.arm_pain);
        ASTHMA = mContext.getString(R.string.asthma);
        BACK_PAIN = mContext.getString(R.string.back_pain);
        BLOOD_IN_URINE = mContext.getString(R.string.blood_in_urine);
        FLANK_PAIN = mContext.getString(R.string.flank_pain);
        CHEST_PAIN = mContext.getString(R.string.chest_pain);

        pocMap.put(DEFAULT_CHOICE, new ArrayList<String>());

        final List<String> abdPain = new ArrayList<>();
        abdPain.add(MEDICAL_EVALUATION);
        abdPain.add(INR);
        abdPain.add(CBC);
        abdPain.add(BMP);
        abdPain.add(CMP);
        abdPain.add(BNP);
        abdPain.add(LACTIC_ACID);
        abdPain.add(AMYLASE);
        abdPain.add(STOOL_CULTURE);
        abdPain.add(LFT);
        abdPain.add(HCG);
        abdPain.add(TSH);
        abdPain.add(URINALYSIS);
        abdPain.add(FLU_SCREENING);
        abdPain.add(FINGER_STICK);
        abdPain.add(X_RAY);
        abdPain.add(IV_THERAPY);
        abdPain.add(IV_FLUID);
        abdPain.add(PAIN_MEDICATION);
        abdPain.add(NAUSEA_MEDICATION);
        abdPain.add(CT_SCAN);
        pocMap.put(ABDOMINAL_PAIN, abdPain);

        final List<String> armPain = new ArrayList<>();
        armPain.add(MEDICAL_EVALUATION);
        abdPain.add(CBC);
        abdPain.add(BMP);
        abdPain.add(INR);
        abdPain.add(LACTIC_ACID);
        abdPain.add(D_DIMER);
        abdPain.add(IV_THERAPY);
        abdPain.add(IV_FLUID);
        abdPain.add(PAIN_MEDICATION);
        abdPain.add(NAUSEA_MEDICATION);
        abdPain.add(X_RAY);
        abdPain.add(CT_SCAN);
        abdPain.add(ULTRASOUND);
        abdPain.add(INPATIENT_CONSULT);
        pocMap.put(ARM_PAIN, armPain);

        final List<String> asthma = new ArrayList<>();
        asthma.add(MEDICAL_EVALUATION);
        asthma.add(EKG);
        asthma.add(OXYGEN);
        asthma.add(BMP);
        asthma.add(CBC);
        asthma.add(D_DIMER);
        asthma.add(TROPONIN);
        asthma.add(BNP);
        asthma.add(LACTIC_ACID);
        asthma.add(BLOOD_GAS);
        asthma.add(IV_THERAPY);
        asthma.add(IV_FLUID);
        asthma.add(PAIN_MEDICATION);
        asthma.add(NAUSEA_MEDICATION);
        asthma.add(X_RAY);
        asthma.add(CT_SCAN);
        asthma.add(ECHO);
        asthma.add(NEBULIZER_TREATMENT);
        asthma.add(PEAK_FLOW_METER);
        asthma.add(INPATIENT_CONSULT);
        pocMap.put(ASTHMA, asthma);

        final List<String> backPain = new ArrayList<>();
        backPain.add(MEDICAL_EVALUATION);
        backPain.add(CBC);
        backPain.add(BMP);
        backPain.add(ESR);
        backPain.add(CRP);
        backPain.add(D_DIMER);
        backPain.add(IV_THERAPY);
        backPain.add(IV_FLUID);
        backPain.add(URINALYSIS);
        backPain.add(PAIN_MEDICATION);
        backPain.add(NAUSEA_MEDICATION);
        backPain.add(X_RAY);
        backPain.add(CT_SCAN);
        backPain.add(MRI);
        backPain.add(INPATIENT_CONSULT);
        pocMap.put(BACK_PAIN, backPain);

        final List<String> bloodInUrine = new ArrayList<>();
        bloodInUrine.add(MEDICAL_EVALUATION);
        bloodInUrine.add(CBC);
        bloodInUrine.add(BMP);
        bloodInUrine.add(INR);
        bloodInUrine.add(HCG);
        bloodInUrine.add(URINALYSIS);
        bloodInUrine.add(IV_THERAPY);
        bloodInUrine.add(IV_FLUID);
        bloodInUrine.add(FOLEY_CATHETER);
        bloodInUrine.add(UROLOGY_CONSULT);
        pocMap.put(BLOOD_IN_URINE, bloodInUrine);

        final List<String> chestPain = new ArrayList<>();
        chestPain.add(MEDICAL_EVALUATION);
        chestPain.add(EKG);
        chestPain.add(INR);
        chestPain.add(CBC);
        chestPain.add(BMP);
        chestPain.add(TROPONIN);
        chestPain.add(D_DIMER);
        chestPain.add(LACTIC_ACID);
        chestPain.add(IV_THERAPY);
        chestPain.add(IV_FLUID);
        chestPain.add(URINALYSIS);
        chestPain.add(PAIN_MEDICATION);
        chestPain.add(NAUSEA_MEDICATION);
        chestPain.add(CHEST_X_RAY);
        chestPain.add(CT_SCAN);
        chestPain.add(STRESS_TEST);
        chestPain.add(ECHO);
        pocMap.put(CHEST_PAIN, chestPain);

        final List<String> flankPain = new ArrayList<>();
        flankPain.add(MEDICAL_EVALUATION);
        flankPain.add(HEADACHE);
        flankPain.add(CBC);
        flankPain.add(BMP);
        flankPain.add(LACTIC_ACID);
        flankPain.add(URINALYSIS);
        flankPain.add(IV);
        flankPain.add(PAIN_MEDICATION);
        flankPain.add(NAUSEA_MEDICATION);
        flankPain.add(CT_SCAN);
        flankPain.add(ULTRASOUND);
        flankPain.add(X_RAY);
        flankPain.add(UROLOGY_CONSULT);
        pocMap.put(FLANK_PAIN, flankPain);

        final List<String> headAche = new ArrayList<>();
        headAche.add(MEDICAL_EVALUATION);
        headAche.add(CBC);
        headAche.add(BMP);
        headAche.add(INR);
        headAche.add(LACTIC_ACID);
        headAche.add(URINALYSIS);
        headAche.add(IV_THERAPY);
        headAche.add(IV_FLUID);
        headAche.add(PAIN_MEDICATION);
        headAche.add(NAUSEA_MEDICATION);
        headAche.add(CT_SCAN);
        headAche.add(X_RAY);
        headAche.add(MRI);
        headAche.add(NEUROSURGERY_CONSULT);
        headAche.add(NEUROSURGERY_CONSULT);
        pocMap.put(HEADACHE, headAche);

        final List<String> legPain = new ArrayList<>();
        legPain.add(MEDICAL_EVALUATION);
        legPain.add(CBC);
        legPain.add(BMP);
        legPain.add(CMP);
        legPain.add(ESR);
        legPain.add(LACTIC_ACID);
        legPain.add(D_DIMER);
        legPain.add(C_REACTIVE_PROTEIN);
        legPain.add(URIC_ACID);
        legPain.add(IV_THERAPY);
        legPain.add(IV_FLUID);
        legPain.add(PAIN_MEDICATION);
        legPain.add(NAUSEA_MEDICATION);
        legPain.add(X_RAY);
        legPain.add(ULTRASOUND);
        legPain.add(CT_SCAN);
        legPain.add(MRI);
        pocMap.put(LEG_PAIN, legPain);

        final List<String> pregProblems = new ArrayList<>();
        pregProblems.add(MEDICAL_EVALUATION);
        pregProblems.add(CBC);
        pregProblems.add(BMP);
        pregProblems.add(INR);
        pregProblems.add(HCG);
        pregProblems.add(IV_THERAPY);
        pregProblems.add(IV_FLUID);
        pregProblems.add(URINALYSIS);
        pregProblems.add(PAIN_MEDICATION);
        pregProblems.add(NAUSEA_MEDICATION);
        pregProblems.add(ULTRASOUND);
        pregProblems.add(PELVIC_EXAM);
        pregProblems.add(OBGYN_CONSULT);
        pocMap.put(PREGNANCY_PROBLEMS, pregProblems);

        final List<String> rectal = new ArrayList<>();
        rectal.add(MEDICAL_EVALUATION);
        rectal.add(CBC);
        rectal.add(BMP);
        rectal.add(INR);
        rectal.add(TYPE_SCREEN);
        rectal.add(IV_THERAPY);
        rectal.add(IV_FLUID);
        rectal.add(URINALYSIS);
        rectal.add(BLOOD_TRANSFUSION);
        rectal.add(CHEST_X_RAY);
        pocMap.put(RECTAL_BLEEDING, rectal);

        final List<String> shortnessBreath = new ArrayList<>();
        shortnessBreath.add(MEDICAL_EVALUATION);
        shortnessBreath.add(EKG);
        shortnessBreath.add(OXYGEN);
        shortnessBreath.add(BMP);
        shortnessBreath.add(CBC);
        shortnessBreath.add(D_DIMER);
        shortnessBreath.add(TROPONIN);
        shortnessBreath.add(BNP);
        shortnessBreath.add(INR);
        shortnessBreath.add(LACTIC_ACID);
        shortnessBreath.add(BLOOD_GAS);
        shortnessBreath.add(IV_PLACEMENT);
        shortnessBreath.add(PAIN_MEDICATION);
        shortnessBreath.add(NAUSEA_MEDICATION);
        shortnessBreath.add(X_RAY);
        shortnessBreath.add(CT_SCAN);
        shortnessBreath.add(ECHO);
        shortnessBreath.add(NEBULIZER_TREATMENT);
        shortnessBreath.add(PEAK_FLOW_METER);
        shortnessBreath.add(INPATIENT_CONSULT);
        pocMap.put(SHORTNESS_OF_BREATH, shortnessBreath);

        final List<String> slurred = new ArrayList<>();
        slurred.add(MEDICAL_EVALUATION);
        slurred.add(CBC);
        slurred.add(BMP);
        slurred.add(D_DIMER);
        slurred.add(TROPONIN);
        slurred.add(LACTIC_ACID);
        slurred.add(AMMONIA);
        slurred.add(INR);
        slurred.add(FINGER_STICK);
        slurred.add(IV);
        slurred.add(URINALYSIS);
        slurred.add(PAIN_MEDICATION);
        slurred.add(NAUSEA_MEDICATION);
        slurred.add(SWALLOW_SCREEN);
        slurred.add(CT_SCAN);
        slurred.add(X_RAY);
        slurred.add(MRI);
        pocMap.put(SLURRED_SPEAK, slurred);

        final List<String> testicular = new ArrayList<>();
        testicular.add(MEDICAL_EVALUATION);
        testicular.add(CBC);
        testicular.add(BMP);
        testicular.add(CMP);
        testicular.add(LACTIC_ACID);
        testicular.add(IV_THERAPY);
        testicular.add(URINALYSIS);
        testicular.add(PAIN_MEDICATION);
        testicular.add(NAUSEA_MEDICATION);
        testicular.add(FOLEY_CATHETER);
        testicular.add(CT_SCAN);
        testicular.add(ULTRASOUND);
        testicular.add(UROLOGY_CONSULT);
        pocMap.put(TESTICULAR_PAIN, testicular);

        final List<String> vaginalBleeding = new ArrayList<>();
        vaginalBleeding.add(MEDICAL_EVALUATION);
        vaginalBleeding.add(CBC);
        vaginalBleeding.add(BMP);
        vaginalBleeding.add(INR);
        vaginalBleeding.add(HCG);
        vaginalBleeding.add(TYPE_SCREEN);
        vaginalBleeding.add(IV_THERAPY);
        vaginalBleeding.add(IV_FLUID);
        vaginalBleeding.add(URINALYSIS);
        vaginalBleeding.add(PELVIC_EXAM);
        vaginalBleeding.add(BLOOD_TRANSFUSION);
        vaginalBleeding.add(PAIN_MEDICATION);
        vaginalBleeding.add(NAUSEA_MEDICATION);
        vaginalBleeding.add(CT_SCAN);
        vaginalBleeding.add(ULTRASOUND);
        vaginalBleeding.add(OBGYN_CONSULT);
        pocMap.put(VAGINAL_BLEEDING, vaginalBleeding);

        final List<String> ams = new ArrayList<>();
        ams.add(MEDICAL_EVALUATION);
        ams.add(INR);
        ams.add(CBC);
        ams.add(BMP);
        ams.add(CMP);
        ams.add(BNP);
        ams.add(ETHANOL);
        ams.add(LIPASE);
        ams.add(AMYLASE);
        ams.add(TROPONIN);
        ams.add(AMMONIA);
        ams.add(LACTIC_ACID);
        ams.add(HCG);
        ams.add(URINALYSIS);
        ams.add(FLU_SCREENING);
        ams.add(FINGER_STICK);
        ams.add(X_RAY);
        ams.add(IV_THERAPY);
        ams.add(IV_FLUID);
        ams.add(PAIN_MEDICATION);
        ams.add(NAUSEA_MEDICATION);
        ams.add(CAT_SCAN);
        ams.add(NEUROLOGY_CONSULT);
        pocMap.put(ALTERED_MENTAL_STATUS, ams);

        final List<String> vomittingBlood = new ArrayList<>();
        vomittingBlood.add(MEDICAL_EVALUATION);
        vomittingBlood.add(INR);
        vomittingBlood.add(CBC);
        vomittingBlood.add(BMP);
        vomittingBlood.add(CMP);
        vomittingBlood.add(TYPE_SCREEN);
        vomittingBlood.add(TROPONIN);
        vomittingBlood.add(LACTIC_ACID);
        vomittingBlood.add(HCG);
        vomittingBlood.add(URINALYSIS);
        vomittingBlood.add(FINGER_STICK);
        vomittingBlood.add(X_RAY);
        vomittingBlood.add(IV_THERAPY);
        vomittingBlood.add(IV_FLUID);
        vomittingBlood.add(PAIN_MEDICATION);
        vomittingBlood.add(NAUSEA_MEDICATION);
        vomittingBlood.add(ANTACID_MEDICATION);
        vomittingBlood.add(CAT_SCAN);
        vomittingBlood.add(GI_CONSULT);
        pocMap.put(VOMITING_BLOOD, vomittingBlood);

        final List<String> nausea = new ArrayList<>();
        nausea.add(MEDICAL_EVALUATION_INR);
        nausea.add(CBC);
        nausea.add(BMP);
        nausea.add(CMP);
        nausea.add(LIPASE);
        nausea.add(ESR);
        nausea.add(AMYLASE);
        nausea.add(TROPONIN);
        nausea.add(LACTIC_ACID);
        nausea.add(STOOL_CULTURE);
        nausea.add(LFT);
        nausea.add(HCG);
        nausea.add(URINALYSIS);
        nausea.add(FINGER_STICK);
        nausea.add(X_RAY);
        nausea.add(IV_THERAPY);
        nausea.add(IV_FLUID);
        nausea.add(PAIN_MEDICATION);
        nausea.add(NAUSEA_MEDICATION);
        nausea.add(CAT_SCAN);
        nausea.add(GI_CONSULT);
        pocMap.put(NAUSEA_VOMITING_DIARRHEA, nausea);

        final List<String> laceration = new ArrayList<>();
        laceration.add(MEDICAL_EVALUATION);
        laceration.add(TETANUS_SHOT);
        laceration.add(PAIN_MEDICATION);
        laceration.add(NAUSEA_MEDICATION);
        laceration.add(STITCHES);
        pocMap.put(LACERATION, laceration);

        final List<String> eye = new ArrayList<>();
        eye.add(MEDICAL_EVALUATION);
        eye.add(EYE_DROPS);
        eye.add(EYE_IRRIGATION);
        eye.add(ANTIBIOTICS);
        pocMap.put(EYE_INJURY, eye);

        final List<String> legSwell = new ArrayList<>();
        legSwell.add(MEDICAL_EVALUATION);
        legSwell.add(EKG);
        legSwell.add(OXYGEN);
        legSwell.add(BMP);
        legSwell.add(CBC);
        legSwell.add(D_DIMER);
        legSwell.add(TROPONIN);
        legSwell.add(BNP);
        legSwell.add(LACTIC_ACID);
        legSwell.add(BLOOD_GAS);
        legSwell.add(IV_THERAPY);
        legSwell.add(IV_FLUID);
        legSwell.add(PAIN_MEDICATION);
        legSwell.add(NAUSEA_MEDICATION);
        legSwell.add(ANTIBIOTICS);
        legSwell.add(X_RAY);
        legSwell.add(CT_SCAN);
        legSwell.add(ULTRASOUND);
        pocMap.put(LEG_SWELLING, legSwell);

        final List<String> facialSwelling = new ArrayList<>();
        facialSwelling.add(MEDICAL_EVALUATION);
        facialSwelling.add(EKG);
        facialSwelling.add(OXYGEN);
        facialSwelling.add(BMP);
        facialSwelling.add(CBC);
        facialSwelling.add(BNP);
        facialSwelling.add(BLOOD_GAS);
        facialSwelling.add(IV_THERAPY);
        facialSwelling.add(IV_FLUID);
        facialSwelling.add(PAIN_MEDICATION);
        facialSwelling.add(NAUSEA_MEDICATION);
        facialSwelling.add(X_RAY);
        facialSwelling.add(CT_SCAN);
        facialSwelling.add(INPATIENT_CONSULT);
        pocMap.put(FACIAL_SWELLING, facialSwelling);

        final List<String> allergicReaction = new ArrayList<>();
        allergicReaction.add(MEDICAL_EVALUATION);
        allergicReaction.add(EKG);
        allergicReaction.add(OXYGEN);
        allergicReaction.add(BMP);
        allergicReaction.add(CBC);
        allergicReaction.add(IV_PLACEMENT);
        allergicReaction.add(PAIN_MEDICATION);
        allergicReaction.add(MONITOR);
        pocMap.put(ALLERGIC_REACTION, allergicReaction);

        final List<String> palpitations = new ArrayList<>();
        palpitations.add(MEDICAL_EVALUATION);
        palpitations.add(EKG);
        palpitations.add(PT);
        palpitations.add(PTT_INR);
        palpitations.add(CBC);
        palpitations.add(BMP);
        palpitations.add(REPEAT_TROPONIN);
        palpitations.add(TROPONIN);
        palpitations.add(D_DIMER);
        palpitations.add(LACTIC_ACID);
        palpitations.add(IV_THERAPY);
        palpitations.add(IV_FLUID);
        palpitations.add(URINALYSIS);
        palpitations.add(CHEST_X_RAY);
        palpitations.add(CT_SCAN);
        palpitations.add(STRESS_TEST);
        palpitations.add(ECHO);
        palpitations.add(HOLTER_MONITOR);
        palpitations.add(CARDIOLOGY_CONSULT);
        palpitations.add(INPATIENT_CONSULT);
        pocMap.put(PALPITATIONS, palpitations);

        final List<String> rash = new ArrayList<>();
        rash.add(MEDICAL_EVALUATION);
        rash.add(CBC);
        rash.add(BMP);
        rash.add(LACTIC_ACID);
        rash.add(IV_INSERTION);
        rash.add(PAIN_MEDICATION);
        pocMap.put(RASH, rash);

        final List<String> overdose = new ArrayList<>();
        overdose.add(CBC);
        rash.add(BMP);
        rash.add(CMP);
        rash.add(TROPONIN);
        rash.add(ACETAMINOPHEN_LEVEL);
        rash.add(ASPIRIN_LEVEL);
        rash.add(LIPASE);
        rash.add(AMYLASE);
        rash.add(PT);
        rash.add(INR);
        rash.add(LACTIC_ACID);
        rash.add(URINALYSIS);
        rash.add(IV_THERAPY);
        rash.add(IV_FLUID);
        rash.add(NAUSEA_MEDICATION);
        rash.add(PAIN_MEDICATION);
        rash.add(CHEST_X_RAY);
        rash.add(CT_SCAN);
        rash.add(SOCIAL_WORK_CONSULT);
        rash.add(INPATIENT_CONSULT);
        pocMap.put(OVERDOSE, overdose);

        final List<String> abnormalLabs = new ArrayList<>();
        abnormalLabs.add(CBC);
        abnormalLabs.add(BMP);
        abnormalLabs.add(CMP);
        abnormalLabs.add(TROPONIN);
        abnormalLabs.add(LIPASE);
        abnormalLabs.add(AMYLASE);
        abnormalLabs.add(PT);
        abnormalLabs.add(INR);
        abnormalLabs.add(LACTIC_ACID);
        abnormalLabs.add(AMMONIA);
        abnormalLabs.add(URINALYSIS);
        abnormalLabs.add(IV_THERAPY);
        abnormalLabs.add(IV_FLUID);
        abnormalLabs.add(NAUSEA_MEDICATION);
        abnormalLabs.add(PAIN_MEDICATION);
        abnormalLabs.add(CHEST_X_RAY);
        abnormalLabs.add(CT_SCAN);
        abnormalLabs.add(INPATIENT_CONSULT);
        pocMap.put(ABNORMAL_LABS, abnormalLabs);

        testDescriptions = new HashMap<>();
        testDescriptions.put(POCValues.DEFAULT_CHOICE, mContext.getString(R.string.no_information));
//        testDescriptions.put(ABDOMINAL_PAIN, mContext.getString(R.string.abdominal_pain_description));
        testDescriptions.put(URINALYSIS, mContext.getString(R.string.urinalysis_description));
//        testDescriptions.put(IV, mContext.getString(R.string.iv_description));
        testDescriptions.put(PAIN_MEDICATION, mContext.getString(R.string.pain_medication_description));
        testDescriptions.put(NAUSEA_MEDICATION, mContext.getString(R.string.nausea_medication_description));
        testDescriptions.put(CT_SCAN, mContext.getString(R.string.ct_scan_description));
        testDescriptions.put(ULTRASOUND, mContext.getString(R.string.ultrasound_description));
        testDescriptions.put(X_RAY, mContext.getString(R.string.x_ray_description));
//        testDescriptions.put(UROLOGY_CONSULT, mContext.getString(R.string.urology_consult_description));
//        testDescriptions.put(HEADACHE, mContext.getString(R.string.headache_description));
//        testDescriptions.put(MEDICAL_EVALUATION, mContext.getString(R.string.medical_evaluation_description));
        testDescriptions.put(CBC, mContext.getString(R.string.cbc_description));
//        testDescriptions.put(CRP, mContext.getString(R.string.crp_description));
        testDescriptions.put(BMP, mContext.getString(R.string.bmp_description));
        testDescriptions.put(INR, mContext.getString(R.string.inr_description));
//        testDescriptions.put(LACTIC_ACID, mContext.getString(R.string.lactic_acid_description));
        testDescriptions.put(IV_THERAPY, mContext.getString(R.string.iv_therapy_description));
//        testDescriptions.put(IV_FLUID, mContext.getString(R.string.iv_fluid_description));
        testDescriptions.put(MRI, mContext.getString(R.string.mri_description));
//        testDescriptions.put(NEUROSURGERY_CONSULT, mContext.getString(R.string.neurosurgery_consult_description));
//        testDescriptions.put(NEUROLOGY_CONSULT, mContext.getString(R.string.neurology_consult_description));
//        testDescriptions.put(LEG_PAIN, mContext.getString(R.string.leg_pain_description));
        testDescriptions.put(CMP, mContext.getString(R.string.cmp_description));
        testDescriptions.put(ESR, mContext.getString(R.string.esr_description));
        testDescriptions.put(D_DIMER, mContext.getString(R.string.d_dimer_description));
//        testDescriptions.put(C_REACTIVE_PROTEIN, mContext.getString(R.string.c_reactive_protein_description));
        testDescriptions.put(URIC_ACID, mContext.getString(R.string.uric_acid_description));
//        testDescriptions.put(ULTRASOUND__, mContext.getString(R.string.ultrasound___description));
//        testDescriptions.put(PREGNANCY_PROBLEMS, mContext.getString(R.string.pregnancy_problems_description));
        testDescriptions.put(HCG, mContext.getString(R.string.hcg_description));
        testDescriptions.put(PELVIC_EXAM, mContext.getString(R.string.pelvic_exam_description));
//        testDescriptions.put(OBGYN_CONSULT, mContext.getString(R.string.obgyn_consult_description));
//        testDescriptions.put(RECTAL_BLEEDING, mContext.getString(R.string.rectal_bleeding_description));
        testDescriptions.put(TYPE_SCREEN, mContext.getString(R.string.type_screen_description));
        testDescriptions.put(BLOOD_TRANSFUSION, mContext.getString(R.string.blood_transfusion_description));
//        testDescriptions.put(CHEST_X_RAY, mContext.getString(R.string.chest_x_ray_description));
//        testDescriptions.put(SHORTNESS_OF_BREATH, mContext.getString(R.string.shortness_of_breath_description));
        testDescriptions.put(EKG, mContext.getString(R.string.ekg_description));
//        testDescriptions.put(OXYGEN, mContext.getString(R.string.oxygen_description));
        testDescriptions.put(TROPONIN, mContext.getString(R.string.troponin_description));
//        testDescriptions.put(BNP, mContext.getString(R.string.bnp_description));
//        testDescriptions.put(BLOOD_GAS, mContext.getString(R.string.blood_gas_description));
        testDescriptions.put(IV_PLACEMENT, mContext.getString(R.string.iv_placement_description));
//        testDescriptions.put(ECHO, mContext.getString(R.string.echo_description));
//        testDescriptions.put(NEBULIZER_TREATMENT, mContext.getString(R.string.nebulizer_treatment_description));
        testDescriptions.put(PEAK_FLOW_METER, mContext.getString(R.string.peak_flow_meter_description));
//        testDescriptions.put(INPATIENT_CONSULT, mContext.getString(R.string.inpatient_consult_description));
//        testDescriptions.put(SLURRED_SPEAK, mContext.getString(R.string.slurred_speak_description));
//        testDescriptions.put(AMMONIA, mContext.getString(R.string.ammonia_description));
//        testDescriptions.put(FINGER_STICK, mContext.getString(R.string.finger_stick_description));
//        testDescriptions.put(SWALLOW_SCREEN, mContext.getString(R.string.swallow_screen_description));
//        testDescriptions.put(TESTICULAR_PAIN, mContext.getString(R.string.testicular_pain_description));
//        testDescriptions.put(FOLEY_CATHETER, mContext.getString(R.string.foley_catheter_description));
//        testDescriptions.put(VAGINAL_BLEEDING, mContext.getString(R.string.vaginal_bleeding_description));
//        testDescriptions.put(ALTERED_MENTAL_STATUS, mContext.getString(R.string.altered_mental_status_description));
//        testDescriptions.put(ETHANOL, mContext.getString(R.string.ethanol_description));
        testDescriptions.put(LIPASE, mContext.getString(R.string.lipase_description));
        testDescriptions.put(AMYLASE, mContext.getString(R.string.amylase_description));
        testDescriptions.put(FLU_SCREENING, mContext.getString(R.string.flu_screening_description));
//        testDescriptions.put(CAT_SCAN, mContext.getString(R.string.cat_scan_description));
//        testDescriptions.put(VOMITING_BLOOD, mContext.getString(R.string.vomiting_blood_description));
//        testDescriptions.put(ANTACID_MEDICATION, mContext.getString(R.string.antacid_medication_description));
//        testDescriptions.put(GI_CONSULT, mContext.getString(R.string.gi_consult_description));
//        testDescriptions.put(NAUSEA_VOMITING_DIARRHEA, mContext.getString(R.string.nausea_vomiting_diarrhea_description));
//        testDescriptions.put(MEDICAL_EVALUATION_INR, mContext.getString(R.string.medical_evaluation_inr_description));
        testDescriptions.put(STOOL_CULTURE, mContext.getString(R.string.stool_culture_description));
        testDescriptions.put(LFT, mContext.getString(R.string.lft_description));
//        testDescriptions.put(LACERATION, mContext.getString(R.string.laceration_description));
        testDescriptions.put(TETANUS_SHOT, mContext.getString(R.string.tetanus_shot_description));
//        testDescriptions.put(STITCHES, mContext.getString(R.string.stitches_description));
//        testDescriptions.put(EYE_INJURY, mContext.getString(R.string.eye_injury_description));
//        testDescriptions.put(EYE_DROPS, mContext.getString(R.string.eye_drops_description));
        testDescriptions.put(EYE_IRRIGATION, mContext.getString(R.string.eye_irrigation_description));
//        testDescriptions.put(ANTIBIOTICS, mContext.getString(R.string.antibiotics_description));
//        testDescriptions.put(LEG_SWELLING, mContext.getString(R.string.leg_swelling_description));
//        testDescriptions.put(FACIAL_SWELLING, mContext.getString(R.string.facial_swelling_description));
//        testDescriptions.put(ALLERGIC_REACTION, mContext.getString(R.string.allergic_reaction_description));
//        testDescriptions.put(MONITOR, mContext.getString(R.string.monitor_description));
//        testDescriptions.put(PALPITATIONS, mContext.getString(R.string.palpitations_description));
//        testDescriptions.put(ECHOCARDIOGRAM, mContext.getString(R.string.echocardiogram_description));
//        testDescriptions.put(PT, mContext.getString(R.string.pt_description));
//        testDescriptions.put(PTT_INR, mContext.getString(R.string.ptt_inr_description));
//        testDescriptions.put(REPEAT_TROPONIN, mContext.getString(R.string.repeat_troponin_description));
        testDescriptions.put(STRESS_TEST, mContext.getString(R.string.stress_test_description));
//        testDescriptions.put(HOLTER_MONITOR, mContext.getString(R.string.holter_monitor_description));
//        testDescriptions.put(CARDIOLOGY_CONSULT, mContext.getString(R.string.cardiology_consult_description));
//        testDescriptions.put(RASH, mContext.getString(R.string.rash_description));
//        testDescriptions.put(IV_INSERTION, mContext.getString(R.string.iv_insertion_description));
//        testDescriptions.put(OVERDOSE, mContext.getString(R.string.overdose_description));
//        testDescriptions.put(ACETAMINOPHEN_LEVEL, mContext.getString(R.string.acetaminophen_level_description));
//        testDescriptions.put(ASPIRIN_LEVEL, mContext.getString(R.string.aspirin_level_description));
//        testDescriptions.put(SOCIAL_WORK_CONSULT, mContext.getString(R.string.social_work_consult_description));
//        testDescriptions.put(ABNORMAL_LABS, mContext.getString(R.string.abnormal_labs_description));
        testDescriptions.put(TSH, mContext.getString(R.string.tsh_description));
//        testDescriptions.put(ARM_PAIN, mContext.getString(R.string.arm_pain_description));
//        testDescriptions.put(ASTHMA, mContext.getString(R.string.asthma_description));
//        testDescriptions.put(BACK_PAIN, mContext.getString(R.string.back_pain_description));
//        testDescriptions.put(BLOOD_IN_URINE, mContext.getString(R.string.blood_in_urine_description));
//        testDescriptions.put(FLANK_PAIN, mContext.getString(R.string.flank_pain_description));
//        testDescriptions.put(CHEST_PAIN, mContext.getString(R.string.chest_pain_description));



    }

}
