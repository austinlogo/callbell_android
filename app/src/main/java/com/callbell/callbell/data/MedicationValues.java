package com.callbell.callbell.data;

import android.content.Context;

import com.callbell.callbell.R;

import java.util.LinkedHashMap;

import javax.inject.Inject;

/**
 * Created by austin on 11/5/15.
 */
public class MedicationValues {

    private Context mContext;

    public static LinkedHashMap<String, String> medicationMap;
    public static LinkedHashMap<String, String> expandedNames;

    public final String ANCEF;
    public final String ASPIRIN;
    public final String ATIVAN;
    public final String AVELOX;
    public final String BENADRYL;
    public final String CIPRO;
    public final String CATAPRES;
    public final String DECADRON;
    public final String DIGOXIN;
    public final String DIPRIVAN;
    public final String DILAUDID;
    public final String HALDOL;
    public final String HEPARIN;
    public final String LASIX;
    public final String LOPRESSOR;
    public final String LOVENOX;
    public final String MORPHINE;
    public final String MOTRIN;
    public final String NITROGLYCERIN;
    public final String PERCOCET;
    public final String PHENERGAN;
    public final String PLAVIX;
    public final String PREDNISONE;
    public final String REGLAN;
    public final String ROCEPHIN;
    public final String SOLU_MEDROL;
    public final String TYLENOL;
    public final String TORADOL;
    public final String UNASYN;
    public final String VALIUM;
    public final String VICODIN;
    public final String ZITHROMAX;
    public final String ZOFRAN;
    public final String ZOSYN;


    @Inject
    public MedicationValues(Context ctx) {
        mContext = ctx;
        medicationMap = new LinkedHashMap<>();
        expandedNames = new LinkedHashMap<>();

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
        expandedNames.put(MORPHINE, mContext.getString(R.string.morphine_expanded));
        expandedNames.put(MOTRIN, mContext.getString(R.string.motrin_expanded));
        expandedNames.put(NITROGLYCERIN, mContext.getString(R.string.nitroglycerin_expanded));
        expandedNames.put(PERCOCET, mContext.getString(R.string.percocet_expanded));
        expandedNames.put(PHENERGAN, mContext.getString(R.string.phenergan_expanded));
        expandedNames.put(PLAVIX, mContext.getString(R.string.plavix_expanded));
        expandedNames.put(PREDNISONE, mContext.getString(R.string.prednisone_expanded));
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
    }
}
