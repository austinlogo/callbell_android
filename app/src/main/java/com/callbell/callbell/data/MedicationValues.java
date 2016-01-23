package com.callbell.callbell.data;

import android.content.Context;

import com.callbell.callbell.R;
import com.callbell.callbell.models.State.BiMap;
//import com.google.common.collect.BiMap;
//import com.google.common.collect.HashBiMap;
//
//import java.math.MathContext;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

/**
 * Created by austin on 11/5/15.
 */
public class MedicationValues {

    private Context mContext;

    public static Map<String, String> medicationMap;
    public static Map<String, String> expandedNames;
    public static BiMap<Integer, String> masterMap;

    public static String ANCEF;
    public static String ASPIRIN;
    public static String ATIVAN;
    public static String AVELOX;
    public static String BENADRYL;
    public static String CIPRO;
    public static String CATAPRES;
    public static String DECADRON;
    public static String DIGOXIN;
    public static String DIPRIVAN;
    public static String DILAUDID;
    public static String HALDOL;
    public static String HEPARIN;
    public static String LASIX;
    public static String LOPRESSOR;
    public static String LOVENOX;
    public static String MORPHINE;
    public static String MOTRIN;
    public static String NITROGLYCERIN;
    public static String PERCOCET;
    public static String PHENERGAN;
    public static String PLAVIX;
    public static String PREDNISONE;
    public static String REGLAN;
    public static String ROCEPHIN;
    public static String SOLU_MEDROL;
    public static String TYLENOL;
    public static String TORADOL;
    public static String UNASYN;
    public static String VALIUM;
    public static String VICODIN;
    public static String ZITHROMAX;
    public static String ZOFRAN;
    public static String ZOSYN;
    public static String ALBUTEROL;
    public static String GI_COCKTAIL;
    public static String ADENOSINE;
    public static String AMOXICILLIN;
    public static String AMBIEN;
    public static String ATENOLOL;
    public static String AUGMENTIN;
    public static String BACTRIM;
    public static String CLINDAMYCIN;
    public static String COUMADIN;
    public static String COMPAZINE;
    public static String DILANTIN;
    public static String DILTIAZEM;
    public static String DOPAMINE;
    public static String EPINEPHRINE;
    public static String FENTANYL;
    public static String FLAGYL;
    public static String FLOMAX;
    public static String KEFLEX;
    public static String LEVAQUIN;
    public static String LINSIOPRIL;
    public static String NARCAN;
    public static String OXYCDONE;
    public static String OXYBUTYNIN;
    public static String SPIRONOLACTONE;
    public static String VERSED;
    public static String XANAX;


    @Inject
    public MedicationValues(Context ctx) {
        mContext = ctx;
        medicationMap = new TreeMap<>();
        expandedNames = new LinkedHashMap<>();
        masterMap = new BiMap<>();

        ANCEF = mContext.getString(R.string.ancef);
        ASPIRIN = mContext.getString(R.string.aspirin);
        ATIVAN = mContext.getString(R.string.ativan);
        AVELOX = mContext.getString(R.string.avelox);
        BENADRYL = mContext.getString(R.string.benadryl);
        CIPRO = mContext.getString(R.string.cipro);
        CATAPRES = mContext.getString(R.string.catapres);
        DECADRON = mContext.getString(R.string.decadron);
        DIGOXIN = mContext.getString(R.string.digoxin);
        DIPRIVAN = mContext.getString(R.string.diprivan);
        DILAUDID = mContext.getString(R.string.dilaudid);
        HALDOL = mContext.getString(R.string.haldol);
        HEPARIN = mContext.getString(R.string.heparin);
        LASIX = mContext.getString(R.string.lasix);
        LOPRESSOR = mContext.getString(R.string.lopressor);
        LOVENOX = mContext.getString(R.string.lovenox);
        MORPHINE = mContext.getString(R.string.morphine);
        MOTRIN = mContext.getString(R.string.motrin);
        NITROGLYCERIN = mContext.getString(R.string.nitroglycerin);
        PERCOCET = mContext.getString(R.string.percocet);
        PHENERGAN = mContext.getString(R.string.phenergan);
        PLAVIX = mContext.getString(R.string.plavix);
        PREDNISONE = mContext.getString(R.string.prednisone);
        REGLAN = mContext.getString(R.string.reglan);
        ROCEPHIN = mContext.getString(R.string.rocephin);
        SOLU_MEDROL = mContext.getString(R.string.solu_medrol);
        TYLENOL = mContext.getString(R.string.tylenol);
        TORADOL = mContext.getString(R.string.toradol);
        UNASYN = mContext.getString(R.string.unasyn);
        VALIUM = mContext.getString(R.string.valium);
        VICODIN = mContext.getString(R.string.vicodin);
        ZITHROMAX = mContext.getString(R.string.zithromax);
        ZOFRAN = mContext.getString(R.string.zofran);
        ZOSYN = mContext.getString(R.string.zosyn);

        ALBUTEROL = mContext.getString(R.string.albuterol);
        GI_COCKTAIL = mContext.getString(R.string.gi_cocktail);
        ADENOSINE = mContext.getString(R.string.adenosine);
        AMOXICILLIN = mContext.getString(R.string.amoxicillin);
        AMBIEN = mContext.getString(R.string.ambien);
        ATENOLOL = mContext.getString(R.string.atenolol);
        AUGMENTIN = mContext.getString(R.string.augmentin);
        BACTRIM = mContext.getString(R.string.bactrim);
        CLINDAMYCIN = mContext.getString(R.string.clindamycin);
        COUMADIN = mContext.getString(R.string.coumadin);
        COMPAZINE = mContext.getString(R.string.compazine);
        DILANTIN = mContext.getString(R.string.dilantin);
        DILTIAZEM = mContext.getString(R.string.diltiazem);
        DOPAMINE = mContext.getString(R.string.dopamine);
        EPINEPHRINE = mContext.getString(R.string.epinephrine);
        FENTANYL = mContext.getString(R.string.fentanyl);
        FLAGYL = mContext.getString(R.string.flagyl);
        FLOMAX = mContext.getString(R.string.flomax);
        KEFLEX = mContext.getString(R.string.keflex);
        LEVAQUIN = mContext.getString(R.string.levaquin);
        LINSIOPRIL = mContext.getString(R.string.linsiopril);
        NARCAN = mContext.getString(R.string.narcan);
        OXYCDONE = mContext.getString(R.string.oxycdone);
        OXYBUTYNIN = mContext.getString(R.string.oxybutynin);
        SPIRONOLACTONE = mContext.getString(R.string.spironolactone);
        VERSED = mContext.getString(R.string.versed);
        XANAX = mContext.getString(R.string.xanax);

        masterMap.put(0x00, ANCEF);
        masterMap.put(0x01, ASPIRIN);
        masterMap.put(0x02, ATIVAN);
        masterMap.put(0x03, AVELOX);
        masterMap.put(0x04, BENADRYL);
        masterMap.put(0x05, CIPRO);
        masterMap.put(0x06, CATAPRES);
        masterMap.put(0x07, DECADRON);
        masterMap.put(0x08, DIGOXIN);
        masterMap.put(0x09, DIPRIVAN);
        masterMap.put(0x0a, DILAUDID);
        masterMap.put(0x0b, HALDOL);
        masterMap.put(0x0c, HEPARIN);
        masterMap.put(0x0d, LASIX);
        masterMap.put(0x0e, LOPRESSOR);
        masterMap.put(0x0f, LOVENOX);
        masterMap.put(0x10, MORPHINE);
        masterMap.put(0x11, MOTRIN);
        masterMap.put(0x12, NITROGLYCERIN);
        masterMap.put(0x13, PERCOCET);
        masterMap.put(0x14, PHENERGAN);
        masterMap.put(0x15, PLAVIX);
        masterMap.put(0x16, PREDNISONE);
        masterMap.put(0x17, REGLAN);
        masterMap.put(0x18, ROCEPHIN);
        masterMap.put(0x19, SOLU_MEDROL);
        masterMap.put(0x1a, TYLENOL);
        masterMap.put(0x1b, TORADOL);
        masterMap.put(0x1c, UNASYN);
        masterMap.put(0x1d, VALIUM);
        masterMap.put(0x1e, VICODIN);
        masterMap.put(0x1f, ZITHROMAX);
        masterMap.put(0x20, ZOFRAN);
        masterMap.put(0x21, ZOSYN);
        masterMap.put(0x22, ALBUTEROL);
        masterMap.put(0x23, GI_COCKTAIL);
        masterMap.put(0x24, ADENOSINE);
        masterMap.put(0x25, AMOXICILLIN);
        masterMap.put(0x26, AMBIEN);
        masterMap.put(0x27, ATENOLOL);
        masterMap.put(0x28, AUGMENTIN);
        masterMap.put(0x29, BACTRIM);
        masterMap.put(0x2a, CLINDAMYCIN);
        masterMap.put(0x2b, COUMADIN);
        masterMap.put(0x2c, COMPAZINE);
        masterMap.put(0x2d, DILANTIN);
        masterMap.put(0x2e, DILTIAZEM);
        masterMap.put(0x2f, DOPAMINE);
        masterMap.put(0x30, EPINEPHRINE);
        masterMap.put(0x31, FENTANYL);
        masterMap.put(0x32, FLAGYL);
        masterMap.put(0x33, FLOMAX);
        masterMap.put(0x34, KEFLEX);
        masterMap.put(0x35, LEVAQUIN);
        masterMap.put(0x36, LINSIOPRIL);
        masterMap.put(0x37, NARCAN);
        masterMap.put(0x38, OXYCDONE);
        masterMap.put(0x39, OXYBUTYNIN);
        masterMap.put(0x3a, SPIRONOLACTONE);
        masterMap.put(0x3b, VERSED);
        masterMap.put(0x3c, XANAX);


        medicationMap.put(ANCEF, mContext.getString(R.string.ancef_description));
        medicationMap.put(ASPIRIN, mContext.getString(R.string.aspirin_description));
        medicationMap.put(ATIVAN, mContext.getString(R.string.ativan_description));
        medicationMap.put(AVELOX, mContext.getString(R.string.avelox_description));
        medicationMap.put(BENADRYL, mContext.getString(R.string.benadryl_description));
        medicationMap.put(CIPRO, mContext.getString(R.string.cipro_description));
        medicationMap.put(CATAPRES, mContext.getString(R.string.catapres_description));
        medicationMap.put(DECADRON, mContext.getString(R.string.decadron_description));
        medicationMap.put(DIGOXIN, mContext.getString(R.string.digoxin_description));
        medicationMap.put(DIPRIVAN, mContext.getString(R.string.diprivan_description));
        medicationMap.put(DILAUDID, mContext.getString(R.string.dilaudid_description));
        medicationMap.put(HALDOL, mContext.getString(R.string.haldol_description));
        medicationMap.put(HEPARIN, mContext.getString(R.string.heparin_description));
        medicationMap.put(LASIX, mContext.getString(R.string.lasix_description));
        medicationMap.put(LOPRESSOR, mContext.getString(R.string.lopressor_description));
        medicationMap.put(LOVENOX, mContext.getString(R.string.lovenox_description));
        medicationMap.put(MORPHINE, mContext.getString(R.string.morphine_description));
        medicationMap.put(MOTRIN, mContext.getString(R.string.motrin_description));
        medicationMap.put(NITROGLYCERIN, mContext.getString(R.string.nitroglycerin_description));
        medicationMap.put(PERCOCET, mContext.getString(R.string.percocet_description));
        medicationMap.put(PHENERGAN, mContext.getString(R.string.phenergan_description));
        medicationMap.put(PLAVIX, mContext.getString(R.string.plavix_description));
        medicationMap.put(PREDNISONE, mContext.getString(R.string.prednisone_description));
        medicationMap.put(REGLAN, mContext.getString(R.string.reglan_description));
        medicationMap.put(ROCEPHIN, mContext.getString(R.string.rocephin_description));
        medicationMap.put(SOLU_MEDROL, mContext.getString(R.string.solu_medrol_description));
        medicationMap.put(TYLENOL, mContext.getString(R.string.tylenol_description));
        medicationMap.put(TORADOL, mContext.getString(R.string.toradol_description));
        medicationMap.put(UNASYN, mContext.getString(R.string.unasyn_description));
        medicationMap.put(VALIUM, mContext.getString(R.string.valium_description));
        medicationMap.put(VICODIN, mContext.getString(R.string.vicodin_description));
        medicationMap.put(ZITHROMAX, mContext.getString(R.string.zithromax_description));
        medicationMap.put(ZOFRAN, mContext.getString(R.string.zofran_description));
        medicationMap.put(ZOSYN, mContext.getString(R.string.zosyn_description));
        medicationMap.put(ADENOSINE, mContext.getString(R.string.adenosine_description));
        medicationMap.put(AMOXICILLIN, mContext.getString(R.string.amoxicillin_description));
        medicationMap.put(AMBIEN, mContext.getString(R.string.ambien_description));
        medicationMap.put(ATENOLOL, mContext.getString(R.string.atenolol_description));
        medicationMap.put(AUGMENTIN, mContext.getString(R.string.augmentin_description));
        medicationMap.put(BACTRIM, mContext.getString(R.string.bactrim_description));
        medicationMap.put(CLINDAMYCIN, mContext.getString(R.string.clindamycin_description));
        medicationMap.put(COUMADIN, mContext.getString(R.string.coumadin_description));
        medicationMap.put(COMPAZINE, mContext.getString(R.string.compazine_description));
        medicationMap.put(DILANTIN, mContext.getString(R.string.dilantin_description));
        medicationMap.put(DILTIAZEM, mContext.getString(R.string.diltiazem_description));
        medicationMap.put(DOPAMINE, mContext.getString(R.string.dopamine_description));
        medicationMap.put(EPINEPHRINE, mContext.getString(R.string.epinephrine_description));
        medicationMap.put(FENTANYL, mContext.getString(R.string.fentanyl_description));
        medicationMap.put(FLAGYL, mContext.getString(R.string.flagyl_description));
        medicationMap.put(FLOMAX, mContext.getString(R.string.flomax_description));
        medicationMap.put(KEFLEX, mContext.getString(R.string.keflex_description));
        medicationMap.put(LEVAQUIN, mContext.getString(R.string.levaquin_description));
        medicationMap.put(LINSIOPRIL, mContext.getString(R.string.linsiopril_description));
        medicationMap.put(NARCAN, mContext.getString(R.string.narcan_description));
        medicationMap.put(OXYCDONE, mContext.getString(R.string.oxycdone_description));
        medicationMap.put(OXYBUTYNIN, mContext.getString(R.string.oxybutynin_description));
        medicationMap.put(SPIRONOLACTONE, mContext.getString(R.string.spironolactone_description));
        medicationMap.put(VERSED, mContext.getString(R.string.versed_description));
        medicationMap.put(XANAX, mContext.getString(R.string.xanax_description));
        medicationMap.put(ALBUTEROL, mContext.getString(R.string.albuterol_description));
        medicationMap.put(GI_COCKTAIL, mContext.getString(R.string.gi_cocktail));

        expandedNames.put(ANCEF, mContext.getString(R.string.ancef_expanded));
        expandedNames.put(ASPIRIN, mContext.getString(R.string.aspirin_expanded));
        expandedNames.put(ATIVAN, mContext.getString(R.string.ativan_expanded));
        expandedNames.put(AVELOX, mContext.getString(R.string.avelox_expanded));
        expandedNames.put(BENADRYL, mContext.getString(R.string.benadryl_expanded));
        expandedNames.put(CIPRO, mContext.getString(R.string.cipro_expanded));
        expandedNames.put(CATAPRES, mContext.getString(R.string.catapres_expanded));
        expandedNames.put(DECADRON, mContext.getString(R.string.decadron_expanded));
        expandedNames.put(DIGOXIN, mContext.getString(R.string.digoxin_expanded));
        expandedNames.put(DIPRIVAN, mContext.getString(R.string.diprivan_expanded));
        expandedNames.put(DILAUDID, mContext.getString(R.string.dilaudid_expanded));
        expandedNames.put(HALDOL, mContext.getString(R.string.haldol_expanded));
        expandedNames.put(HEPARIN, mContext.getString(R.string.heparin_expanded));
        expandedNames.put(LASIX, mContext.getString(R.string.lasix_expanded));
        expandedNames.put(LOPRESSOR, mContext.getString(R.string.lopressor_expanded));
        expandedNames.put(LOVENOX, mContext.getString(R.string.lovenox_expanded));
        expandedNames.put(MOTRIN, mContext.getString(R.string.motrin_expanded));
        expandedNames.put(NITROGLYCERIN, mContext.getString(R.string.nitroglycerin_expanded));
        expandedNames.put(PERCOCET, mContext.getString(R.string.percocet_expanded));
        expandedNames.put(PHENERGAN, mContext.getString(R.string.phenergan_expanded));
        expandedNames.put(PLAVIX, mContext.getString(R.string.plavix_expanded));
        expandedNames.put(REGLAN, mContext.getString(R.string.reglan_expanded));
        expandedNames.put(ROCEPHIN, mContext.getString(R.string.rocephin_expanded));
        expandedNames.put(SOLU_MEDROL, mContext.getString(R.string.solu_medrol_expanded));
        expandedNames.put(TYLENOL, mContext.getString(R.string.tylenol_expanded));
        expandedNames.put(TORADOL, mContext.getString(R.string.toradol_expanded));
        expandedNames.put(UNASYN, mContext.getString(R.string.unasyn_expanded));
        expandedNames.put(VALIUM, mContext.getString(R.string.valium_expanded));
        expandedNames.put(VICODIN, mContext.getString(R.string.vicodin_expanded));
        expandedNames.put(ZITHROMAX, mContext.getString(R.string.zithromax_expanded));
        expandedNames.put(ZOFRAN, mContext.getString(R.string.zofran_expanded));
        expandedNames.put(ZOSYN, mContext.getString(R.string.zosyn_expanded));
        expandedNames.put(ADENOSINE, mContext.getString(R.string.adenosine_expanded));
        expandedNames.put(AMOXICILLIN, mContext.getString(R.string.amoxicillin_expanded));
        expandedNames.put(AMBIEN, mContext.getString(R.string.ambien_expanded));
        expandedNames.put(ATENOLOL, mContext.getString(R.string.atenolol_expanded));
        expandedNames.put(AUGMENTIN, mContext.getString(R.string.augmentin_expanded));
        expandedNames.put(BACTRIM, mContext.getString(R.string.bactrim_expanded));
        expandedNames.put(CLINDAMYCIN, mContext.getString(R.string.clindamycin_expanded));
        expandedNames.put(COUMADIN, mContext.getString(R.string.coumadin_expanded));
        expandedNames.put(COMPAZINE, mContext.getString(R.string.compazine_expanded));
        expandedNames.put(DILANTIN, mContext.getString(R.string.dilantin_expanded));
        expandedNames.put(DILTIAZEM, mContext.getString(R.string.diltiazem_expanded));
        expandedNames.put(DOPAMINE, mContext.getString(R.string.dopamine_expanded));
        expandedNames.put(EPINEPHRINE, mContext.getString(R.string.epinephrine_expanded));
        expandedNames.put(FENTANYL, mContext.getString(R.string.fentanyl_expanded));
        expandedNames.put(FLAGYL, mContext.getString(R.string.flagyl_expanded));
        expandedNames.put(FLOMAX, mContext.getString(R.string.flomax_expanded));
        expandedNames.put(KEFLEX, mContext.getString(R.string.keflex_expanded));
        expandedNames.put(LEVAQUIN, mContext.getString(R.string.levaquin_expanded));
        expandedNames.put(LINSIOPRIL, mContext.getString(R.string.linsiopril_expanded));
        expandedNames.put(NARCAN, mContext.getString(R.string.narcan_expanded));
        expandedNames.put(OXYCDONE, mContext.getString(R.string.oxycdone_expanded));
        expandedNames.put(OXYBUTYNIN, mContext.getString(R.string.oxybutynin_expanded));
        expandedNames.put(SPIRONOLACTONE, mContext.getString(R.string.spironolactone_expanded));
        expandedNames.put(VERSED, mContext.getString(R.string.versed_expanded));
        expandedNames.put(XANAX, mContext.getString(R.string.xanax_expanded));
    }
}
