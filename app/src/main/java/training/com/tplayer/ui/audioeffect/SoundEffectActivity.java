package training.com.tplayer.ui.audioeffect;

import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Virtualizer;
import android.os.RemoteException;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.utils.LogUtils;
import training.com.tplayer.utils.preferences.AudioFxPreference;

import static android.R.attr.value;

/**
 * Created by hnc on 16/05/2017.
 */

public class SoundEffectActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
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

    private LinearLayout mLinearLayout;

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

        mLinearLayout = (LinearLayout) findViewById(R.id.act_sound_eff_layout_equalizer);
    }


    @Override
    public void onActivityCreated() {

        mCheckPresetReverb.setChecked(AudioFxPreference.getInstance().isEnablePresetReverb());
        mCheckBassBoost.setChecked(AudioFxPreference.getInstance().isEnableBassBoost());
        mCheckVirtualizer.setChecked(AudioFxPreference.getInstance().isEnableVirtualizer());

        mCheckPresetReverb.setOnCheckedChangeListener(this);
        mCheckBassBoost.setOnCheckedChangeListener(this);
        mCheckVirtualizer.setOnCheckedChangeListener(this);

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

            mPresetReverb = new PresetReverb(100000000, mAudioSession);
            mBassBoost = new BassBoost(1000, mAudioSession);
            mEqualizer = new Equalizer(1000, mAudioSession);
            mVirtualizer = new Virtualizer(10000, mAudioSession);

            boolean isPresetEnable = AudioFxPreference.getInstance().isEnablePresetReverb();
            LogUtils.printLog("isPresetEnable : " + isPresetEnable);
            initEffectPresetReverb();
            if (isPresetEnable) {
                mPresetReverb.setEnabled(true);
            }

            boolean isBassEnable = AudioFxPreference.getInstance().isEnableBassBoost();
            LogUtils.printLog("isBassEnable : " + isBassEnable);
            if (isBassEnable) {
                mBassBoost.setEnabled(true);
                initEffectBassBoost();
            }

            boolean isVirtualizerEnable = AudioFxPreference.getInstance().isEnableVirtualizer();
            LogUtils.printLog("isVirtualizerEnable : " + isVirtualizerEnable);
            if (isVirtualizerEnable) {
                mVirtualizer.setEnabled(true);
                initEffectVirtualizer();
            }


            mEqualizer.setEnabled(true);
            initEffectEqualizer();
            setupEqualizerFxAndUI();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private void initEffectPresetReverb() {
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

        ArrayAdapter<String> presetReverbAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mKeyPreset);
        presetReverbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnPresetReverb.setAdapter(presetReverbAdapter);

        if (mPresetReverb != null) {
            int currentPreset = mPresetReverb.getPreset();
            mSpnPresetReverb.setSelection(currentPreset);
        }

        mSpnPresetReverb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPresetReverb.setPreset(mValuePreset.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initEffectBassBoost() {

        BassBoost.Settings tempSetting = mBassBoost.getProperties();
        BassBoost.Settings mySetting = new BassBoost.Settings(tempSetting.toString());
        mySetting.strength = MAX_STRENGTH_FOR_BASS;
        mBassBoost.setProperties(mySetting);
        mSeekBassBoost.setMax(1000);


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
                    mBassBoost.setStrength((short) value);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void initEffectVirtualizer() {
        Virtualizer.Settings tempSetting = mVirtualizer.getProperties();
        Virtualizer.Settings mySetting = new Virtualizer.Settings(tempSetting.toString());
        mySetting.strength = MAX_STRENGTH_FOR_VIRTUALIZER;
        mVirtualizer.setProperties(mySetting);
        mSeekVirtualizer.setMax(MAX_STRENGTH_FOR_VIRTUALIZER);


    }


    private void initEffectEqualizer() {
        LogUtils.printLog("Equalizer");
        LogUtils.printLog(mEqualizer.getBandLevelRange()[0] + " - " + mEqualizer.getBandLevelRange()[1]);
        LogUtils.printLog(mEqualizer.getNumberOfPresets() + " - " + mEqualizer.getNumberOfBands());
    }

    private void setupEqualizerFxAndUI() throws RemoteException {
        // Create the Equalizer object (an AudioEffect subclass) and attach it to our media player,
        // with a default priority (0).
        mEqualizer = new Equalizer(0, getPlayerService().getAudioSS());
        mEqualizer.setEnabled(true);

        TextView eqTextView = new TextView(this);
        eqTextView.setText("Equalizer:");
        mLinearLayout.addView(eqTextView);

        short bands = mEqualizer.getNumberOfBands();

        final short minEQLevel = mEqualizer.getBandLevelRange()[0];
        final short maxEQLevel = mEqualizer.getBandLevelRange()[1];

        for (short i = 0; i < bands; i++) {
            final short band = i;

            TextView freqTextView = new TextView(this);
            freqTextView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            freqTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            freqTextView.setText((mEqualizer.getCenterFreq(band) / 1000) + " Hz");
            mLinearLayout.addView(freqTextView);

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView minDbTextView = new TextView(this);
            minDbTextView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            minDbTextView.setText((minEQLevel / 100) + " dB");

            TextView maxDbTextView = new TextView(this);
            maxDbTextView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            maxDbTextView.setText((maxEQLevel / 100) + " dB");

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            SeekBar bar = new SeekBar(this);
            bar.setLayoutParams(layoutParams);
            bar.setMax(maxEQLevel - minEQLevel);
            bar.setProgress(mEqualizer.getBandLevel(band));

            bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    mEqualizer.setBandLevel(band, (short) (progress + minEQLevel));
                }

                public void onStartTrackingTouch(SeekBar seekBar) {}
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            row.addView(minDbTextView);
            row.addView(bar);
            row.addView(maxDbTextView);

            mLinearLayout.addView(row);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.act_sound_eff_chkbox_preset_reverb:
                AudioFxPreference.getInstance().setEnablePresetReverb(isChecked);
                mPresetReverb.setEnabled(isChecked);
                updateUiPreset(isChecked);
                break;
            case R.id.act_sound_eff_chkbox_bass_boost:
                AudioFxPreference.getInstance().setEnableBassBoost(isChecked);
                mBassBoost.setEnabled(isChecked);
                updateUiBassBoost(isChecked);
                break;
            case R.id.act_sound_eff_chkbox_virtualizer:
                AudioFxPreference.getInstance().setEnableVirtualizer(isChecked);
                mVirtualizer.setEnabled(isChecked);
                updateUiVirtualizer(isChecked);
                break;
        }
    }

    private void updateUiPreset(boolean isEnable) {
        if (isEnable) {
            mSpnPresetReverb.setAlpha(1);
        } else {
            mSpnPresetReverb.setAlpha(0.1f);
        }
        mSpnPresetReverb.setClickable(isEnable);
        mSpnPresetReverb.setEnabled(isEnable);
    }

    private void updateUiBassBoost(boolean isEnable) {
        if (isEnable) {
            mSeekBassBoost.setAlpha(1);
        } else {
            mSeekBassBoost.setAlpha(0.1f);
        }
        mSeekBassBoost.setClickable(isEnable);
        mSeekBassBoost.setEnabled(isEnable);
    }

    private void updateUiVirtualizer(boolean isEnable) {
        if (isEnable) {
            mSeekVirtualizer.setAlpha(1);
        } else {
            mSeekVirtualizer.setAlpha(0.1f);
        }
        mSeekVirtualizer.setClickable(isEnable);
        mSeekVirtualizer.setEnabled(isEnable);
    }
}
