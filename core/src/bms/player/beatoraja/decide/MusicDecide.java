package bms.player.beatoraja.decide;

import bms.player.beatoraja.*;
import bms.player.beatoraja.input.BMSPlayerInputProcessor;
import bms.player.beatoraja.input.KeyBoardInputProcesseor.ControlKeys;
import bms.player.beatoraja.skin.*;

import static bms.player.beatoraja.skin.SkinProperty.*;

/**
 * 曲決定部分。
 * 
 * @author exch
 */
public class MusicDecide extends MainState {

	private boolean cancel;

	public static final int SOUND_DECIDE = 0;
	
	public MusicDecide(MainController main) {
		super(main);
	}

	public void create() {
		cancel = false;
		
		loadSkin(SkinType.DECIDE);

		resource.setOrgGaugeOption(resource.getPlayerConfig().getGauge());
	}

	public void prepare() {
		super.prepare();
		setSound(SOUND_DECIDE, "decide.wav", SoundType.BGM, false);
		play(SOUND_DECIDE);
	}

	public void render() {
		long nowtime = timer.getNowTime();
        if(nowtime >getSkin().getInput()){
        	timer.switchTimer(TIMER_STARTINPUT, true);
        }
		if (timer.isTimerOn(TIMER_FADEOUT)) {
			if (timer.getNowTime(TIMER_FADEOUT) > getSkin().getFadeout()) {
				main.changeState(cancel ? MainStateType.MUSICSELECT : MainStateType.PLAY);
			}
		} else {
			if (nowtime > getSkin().getScene()) {
				timer.setTimerOn(TIMER_FADEOUT);
			}
		}
	}

	public void input() {
		if (!timer.isTimerOn(TIMER_FADEOUT) && timer.isTimerOn(TIMER_STARTINPUT)) {
			BMSPlayerInputProcessor input = main.getInputProcessor();
			if (input.getKeyState(0) || input.getKeyState(2) || input.getKeyState(4) || input.getKeyState(6) || input.isControlKeyPressed(ControlKeys.ENTER)) {
				timer.setTimerOn(TIMER_FADEOUT);
			}
			if (input.isControlKeyPressed(ControlKeys.ESCAPE) || (input.startPressed() && input.isSelectPressed())) {
				cancel = true;
				main.getAudioProcessor().setGlobalPitch(1f);
				timer.setTimerOn(TIMER_FADEOUT);
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
