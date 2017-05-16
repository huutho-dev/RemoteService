package training.com.tplayer.ui.audioeffect;

import android.media.audiofx.AudioEffect;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Virtualizer;
import android.os.RemoteException;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.utils.LogUtils;

import static android.R.attr.value;

/**
 * Created by hnc on 16/05/2017.
 */

public class SoundEffectActivity extends BaseActivity  {
    // Preset Reverb
    // Bass boost
    // Virtualizer
    // Equalizer

    private final short PRESET_NONE = PresetReverb.PRESET_NONE;
    private final short PRESET_LARGEHALL = PresetReverb.PRESET_LARGEHALL;
    private final short PRESET_MEDIUMHALL = PresetReverb.PRESET_MEDIUMHALL;
    private final short PRESET_LARGEROOM = PresetReverb.PRESET_LARGEROOM;
    private final short PRESET_MEDIUMROOM = PresetReverb.PRESET_MEDIUMROOM;
    private final short PRESET_SMALLROOM = PresetReverb.PRESET_SMALLROOM;
    private final short PRESET_PLATE = PresetReverb.PRESET_PLATE;

    private final int MAX_STRENGTH_FOR_BASS = 1000;
    private final int MAX_STRENGTH_FOR_VIRTUALIZER = 1000;


    private PresetReverb mPresetReverb;
    private BassBoost mBassBoost;
    private Virtualizer mVirtualizer;
    private Equalizer mEqualizer;

    private int mAudioSession = 0;


    private List<String> mKeyPreset = new ArrayList<>();
    private List<Short> mValuePreset = new ArrayList<>();

    @BindView(R.id.act_sound_eff_chkbox_preset_reverb)
    AppCompatCheckBox mCheckPresetReverb;

    @BindView(R.id.act_sound_eff_spn_preset_reverb)
    AppCompatSpinner mSpnPresetReverb;


    @BindView(R.id.act_sound_eff_chkbox_bass_boost)
    AppCompatCheckBox mCheckBassBoost;

    @BindView(R.id.act_sound_eff_seekbar_bass_boost)
    SeekBar mSeekBassBoost;

    @BindView(R.id.act_sound_eff_chkbox_virtualizer)
    AppCompatCheckBox mCheckVirtualizer;

    @BindView(R.id.act_sound_eff_seekbar_virtualizer)
    DiscreteSeekBar mSeekVirtualizer;


    @Override
    public int setLayoutId() {
        return R.layout.act_sound_eff;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);
    }


    @Override
    public void onActivityCreated() {



    }


    @Override
    protected void createPresenterImpl() {


    }


    @Override
    public void serviceConnected() {
        super.serviceConnected();

        try {
            mAudioSession = getPlayerService().getAudioSS();
            LogUtils.printLog("Audio ss : " + mAudioSession);

            mPresetReverb = new PresetReverb(1000000, mAudioSession);
            mPresetReverb.setEnabled(true);

            mBassBoost = new BassBoost(1000000, mAudioSession);
            mBassBoost.setEnabled(true);

            mVirtualizer = new Virtualizer(1000000, mAudioSession);
            mVirtualizer.setEnabled(true);

            mEqualizer = new Equalizer(1000000, mAudioSession);
            mEqualizer.setEnabled(true);

            initDataPresetReverb();
            initEffectPresetReverb();

            initEffectBassBoost();

            initEffectVirtualizer();

            initEffectEqualizer();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void initDataPresetReverb() {
        mKeyPreset.add("Preset None");
        mKeyPreset.add("Preset Large hall");
        mKeyPreset.add("Preset Medium hall");
        mKeyPreset.add("Preset Large room");
        mKeyPreset.add("Preset Medium room");
        mKeyPreset.add("Preset Small room");
        mKeyPreset.add("Preset Plate ");

        mValuePreset.add(PRESET_NONE);
        mValuePreset.add(PRESET_LARGEHALL);
        mValuePreset.add(PRESET_MEDIUMHALL);
        mValuePreset.add(PRESET_LARGEROOM);
        mValuePreset.add(PRESET_MEDIUMROOM);
        mValuePreset.add(PRESET_SMALLROOM);
        mValuePreset.add(PRESET_PLATE);

    }

    private void initEffectPresetReverb() {

        ArrayAdapter<String> presetReverbAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mKeyPreset);
        presetReverbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnPresetReverb.setAdapter(presetReverbAdapter);
        mSpnPresetReverb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPresetReverb.setPreset(mValuePreset.get(position));
                LogUtils.printLog("position : " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /*.......................................................................................*/

    private void initEffectBassBoost() {

        BassBoost.Settings tempSetting = mBassBoost.getProperties();
        BassBoost.Settings mySetting = new BassBoost.Settings(tempSetting.toString());
        mySetting.strength = MAX_STRENGTH_FOR_BASS;
        mBassBoost.setProperties(mySetting);
        mSeekBassBoost.setMax(1000);


        final AudioEffect.Descriptor[] effects = AudioEffect.queryEffects();

// Determine available/supported effects
        for (final AudioEffect.Descriptor effect : effects) {
            LogUtils.printLog( effect.name.toString() + ", type: " + effect.type.toString());
        }

        mCheckBassBoost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils
                        .printLog("Current" + mSeekBassBoost.getProgress());
            }
        });

        mSeekBassBoost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtils.printLog("my value : " + progress);

                if (fromUser) {
                    // int > short
                    mBassBoost.setStrength((short) value);
                    try {
                        getPlayerService().setBassBoost(mBassBoost.getId());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        mSeekBassBoost.setOnSeekBarChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
//            @Override
//            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
//
//                LogUtils.printLog("my value : " + value);
//
//                if (fromUser) {
//                    mSeekBassBoost.setIndicatorFormatter(String.valueOf(value));
//                    // int > short
//                    mBassBoost.setStrength((short) value);
//                    try {
//                        getPlayerService().setBassBoost(mBassBoost.getId());
//                        LogUtils.printLog("value : " + value);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
//                LogUtils.printLog("my value : " + seekBar.getProgress());
//            }
//
//            @Override
//            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
//                LogUtils.printLog("my value : " +  seekBar.getProgress());
//            }
//        });
    }

    /*.......................................................................................*/

    private void initEffectVirtualizer() {
        Virtualizer.Settings tempSetting = mVirtualizer.getProperties();
        Virtualizer.Settings mySetting = new Virtualizer.Settings(tempSetting.toString());
        mySetting.strength = MAX_STRENGTH_FOR_VIRTUALIZER;
        mVirtualizer.setProperties(mySetting);
        mSeekVirtualizer.setMax(MAX_STRENGTH_FOR_VIRTUALIZER);


    }

    /*.......................................................................................*/

    private void initEffectEqualizer() {
        LogUtils.printLog("Equalizer");
        LogUtils.printLog(mEqualizer.getBandLevelRange()[0] + " - " + mEqualizer.getBandLevelRange()[1]);
        LogUtils.printLog(mEqualizer.getNumberOfPresets() + " - " + mEqualizer.getNumberOfBands());
    }

}
