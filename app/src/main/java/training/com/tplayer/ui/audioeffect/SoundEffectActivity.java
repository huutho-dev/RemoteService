package training.com.tplayer.ui.audioeffect;

import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Virtualizer;
import android.media.audiofx.Visualizer;
import android.os.RemoteException;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.utils.LogUtils;
import training.com.tplayer.utils.preferences.AudioFxPreference;

import static android.R.attr.value;

/**
 * Created by hnc on 16/05/2017.
 */

public class SoundEffectActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
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

    private int mAudioSession = 0;


    private List<String> mKeyPreset = new ArrayList<>();
    private List<Short> mValuePreset = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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

    @BindView(R.id.act_sound_eff_chkbox_equalizer)
    AppCompatCheckBox mCheckEqualizer;

    @BindView(R.id.act_sound_eff_seekbar_virtualizer)
    SeekBar mSeekVirtualizer;

    @BindView(R.id.layout_equalizer)
    LinearLayout mLayoutEQ;


    // EQ
    @BindView(R.id.act_sound_eff_spn_equalizer)
    TextViewRoboto mFlatButton;

    private Equalizer mEqualizer;
    private Visualizer visualizer ;

    private int min_level = 0;
    private int max_level = 100;
    private final int MAX_SLIDERS = 8; // Must match the XML layout
    private SeekBar sliders[] = new SeekBar[MAX_SLIDERS];
    private TextView slider_labels[] = new TextView[MAX_SLIDERS];
    int num_sliders = 0;


    @Override
    public int setLayoutId() {
        return R.layout.act_sound_eff;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Audio Fx");

        sliders[0] = (SeekBar) findViewById(R.id.slider_1);
        slider_labels[0] = (TextView) findViewById(R.id.slider_label_1);
        sliders[1] = (SeekBar) findViewById(R.id.slider_2);
        slider_labels[1] = (TextView) findViewById(R.id.slider_label_2);
        sliders[2] = (SeekBar) findViewById(R.id.slider_3);
        slider_labels[2] = (TextView) findViewById(R.id.slider_label_3);
        sliders[3] = (SeekBar) findViewById(R.id.slider_4);
        slider_labels[3] = (TextView) findViewById(R.id.slider_label_4);
        sliders[4] = (SeekBar) findViewById(R.id.slider_5);
        slider_labels[4] = (TextView) findViewById(R.id.slider_label_5);
        sliders[5] = (SeekBar) findViewById(R.id.slider_6);
        slider_labels[5] = (TextView) findViewById(R.id.slider_label_6);
        sliders[6] = (SeekBar) findViewById(R.id.slider_7);
        slider_labels[6] = (TextView) findViewById(R.id.slider_label_7);
        sliders[7] = (SeekBar) findViewById(R.id.slider_8);
        slider_labels[7] = (TextView) findViewById(R.id.slider_label_8);

    }

    private void updateUiEQ() {
        updateSliders();
    }

    private void updateSliders() {
        for (int i = 0; i < num_sliders; i++) {
            int level;
            if (mEqualizer != null)
                level = mEqualizer.getBandLevel((short) i);
            else
                level = 0;
            int pos = 100 * level / (max_level - min_level) + 50;
            sliders[i].setProgress(pos);
        }
    }

    public void setFlat() {
        if (mEqualizer != null) {
            for (int i = 0; i < num_sliders; i++) {
                mEqualizer.setBandLevel((short) i, (short) 0);
            }
        }
        updateUiEQ();
    }


    @Override
    public void onActivityCreated() {

        mCheckPresetReverb.setChecked(AudioFxPreference.getInstance().isEnablePresetReverb());
        mCheckBassBoost.setChecked(AudioFxPreference.getInstance().isEnableBassBoost());
        mCheckVirtualizer.setChecked(AudioFxPreference.getInstance().isEnableVirtualizer());
        mCheckEqualizer.setChecked(AudioFxPreference.getInstance().isEnableEqualizer());

        mCheckPresetReverb.setOnCheckedChangeListener(this);
        mCheckBassBoost.setOnCheckedChangeListener(this);
        mCheckVirtualizer.setOnCheckedChangeListener(this);
        mCheckEqualizer.setOnCheckedChangeListener(this);


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

            visualizer = new Visualizer(mAudioSession);
            visualizer.setEnabled(true);

            mPresetReverb = new PresetReverb(1, 0);
            mBassBoost = new BassBoost(1, mAudioSession);
            mVirtualizer = new Virtualizer(1, mAudioSession);
            mEqualizer = new Equalizer(1, mAudioSession);




            boolean isEQEnable = AudioFxPreference.getInstance().isEnablePresetReverb();
            LogUtils.printLog("isPresetEnable : " + isEQEnable);
            if (isEQEnable) {
                mEqualizer.setEnabled(true);
                mLayoutEQ.setEnabled(true);
                mLayoutEQ.setClickable(true);
            }else {
                mEqualizer.setEnabled(false);
                mLayoutEQ.setEnabled(false);
                mLayoutEQ.setClickable(false);
            }
            initEq();
            updateUiEQ(AudioFxPreference.getInstance().isEnableEqualizer());


            boolean isPresetEnable = AudioFxPreference.getInstance().isEnablePresetReverb();
            LogUtils.printLog("isPresetEnable : " + isPresetEnable);
            initEffectPresetReverb();
            if (isPresetEnable) {
                mPresetReverb.setEnabled(true);
                mSpnPresetReverb.setEnabled(true);
            }else {
                mPresetReverb.setEnabled(false);
                mSpnPresetReverb.setEnabled(false);
            }

            boolean isBassEnable = AudioFxPreference.getInstance().isEnableBassBoost();
            LogUtils.printLog("isBassEnable : " + isBassEnable);
            if (isBassEnable) {
                mBassBoost.setEnabled(true);
                mSeekBassBoost.setEnabled(true);
                initEffectBassBoost();
            }else {
                mBassBoost.setEnabled(false);
                mSeekBassBoost.setEnabled(false);
            }

            boolean isVirtualizerEnable = AudioFxPreference.getInstance().isEnableVirtualizer();
            LogUtils.printLog("isVirtualizerEnable : " + isVirtualizerEnable);
            if (isVirtualizerEnable) {
                mVirtualizer.setEnabled(true);
                mSeekVirtualizer.setEnabled(true);
                initEffectVirtualizer();
            }else {
                mVirtualizer.setEnabled(false);
                mSeekVirtualizer.setEnabled(false);
            }




        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void initEq() {
        mEqualizer.setEnabled(true);
        int num_bands = mEqualizer.getNumberOfBands();
        num_sliders = num_bands;
        short r[] = mEqualizer.getBandLevelRange();
        min_level = r[0];
        max_level = r[1];

        for (int i = 0; i < num_sliders && i < MAX_SLIDERS; i++) {
            int[] freq_range = mEqualizer.getBandFreqRange((short) i);
            sliders[i].setOnSeekBarChangeListener(this);
            slider_labels[i].setText(formatBandLabel(freq_range));
        }

        for (int i = num_sliders; i < MAX_SLIDERS; i++) {
            sliders[i].setVisibility(View.GONE);
            slider_labels[i].setVisibility(View.GONE);
        }

        mFlatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFlat();
            }
        });
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
                mPresetReverb.setEnabled(true);
                try {
                    getPlayerService().setBassBoost(mPresetReverb.getId());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mPresetReverb.setPreset(mValuePreset.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initEffectBassBoost() {
        mSeekBassBoost.setMax(MAX_STRENGTH_FOR_BASS);
        mSeekBassBoost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mBassBoost.setEnabled(true);
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
        mSeekVirtualizer.setMax(MAX_STRENGTH_FOR_VIRTUALIZER);
        mSeekVirtualizer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mVirtualizer.setEnabled(true);
                    mVirtualizer.setStrength((short) value);
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
            case R.id.act_sound_eff_chkbox_equalizer:
                AudioFxPreference.getInstance().setEnableEqualizer(isChecked);
                mEqualizer.setEnabled(isChecked);
                updateUiEQ(isChecked);
                break;
        }
    }

    private void updateUiEQ(boolean isEnable) {
        if (isEnable) {
            mLayoutEQ.setAlpha(1);
        } else {
            mLayoutEQ.setAlpha(0.4f);
        }
        mLayoutEQ.setClickable(isEnable);
        mLayoutEQ.setEnabled(isEnable);
        mEqualizer.setEnabled(isEnable);
    }

    private void updateUiPreset(boolean isEnable) {
        if (isEnable) {
            mSpnPresetReverb.setAlpha(1);
        } else {
            mSpnPresetReverb.setAlpha(0.4f);
        }
        mSpnPresetReverb.setClickable(isEnable);
        mSpnPresetReverb.setEnabled(isEnable);
    }

    private void updateUiBassBoost(boolean isEnable) {
        if (isEnable) {
            mSeekBassBoost.setAlpha(1);
        } else {
            mSeekBassBoost.setAlpha(0.4f);
        }
        mSeekBassBoost.setClickable(isEnable);
        mSeekBassBoost.setEnabled(isEnable);
    }

    private void updateUiVirtualizer(boolean isEnable) {
        if (isEnable) {
            mSeekVirtualizer.setAlpha(1);
        } else {
            mSeekVirtualizer.setAlpha(0.4f);
        }
        mSeekVirtualizer.setClickable(isEnable);
        mSeekVirtualizer.setEnabled(isEnable);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mEqualizer != null) {
            int new_level = min_level + (max_level - min_level) * progress / 100;

            for (int i = 0; i < num_sliders; i++) {
                if (sliders[i] == seekBar) {
                    mEqualizer.setBandLevel((short) i, (short) new_level);
                    mEqualizer.setEnabled(true);
                    break;
                }
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public String formatBandLabel(int[] band) {
        return milliHzToString(band[0]) + "-" + milliHzToString(band[1]);
    }
    public String milliHzToString(int milliHz) {
        if (milliHz < 1000) return "";
        if (milliHz < 1000000)
            return "" + (milliHz / 1000) + "Hz";
        else
            return "" + (milliHz / 1000000) + "kHz";
    }

}
