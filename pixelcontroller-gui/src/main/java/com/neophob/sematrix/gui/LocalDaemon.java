package com.neophob.sematrix.gui;

import java.util.List;
import java.util.Observer;

import com.neophob.sematrix.PixConServer;
import com.neophob.sematrix.core.api.CallbackMessageInterface;
import com.neophob.sematrix.core.api.PixelController;
import com.neophob.sematrix.core.api.impl.PixelControllerFactory;
import com.neophob.sematrix.core.jmx.TimeMeasureItemGlobal;
import com.neophob.sematrix.core.listener.MessageProcessor;
import com.neophob.sematrix.core.output.IOutput;
import com.neophob.sematrix.core.preset.PresetSettings;
import com.neophob.sematrix.core.properties.ApplicationConfigurationHelper;
import com.neophob.sematrix.core.sound.ISound;
import com.neophob.sematrix.core.visual.MatrixData;
import com.neophob.sematrix.core.visual.OutputMapping;
import com.neophob.sematrix.core.visual.color.ColorSet;

public class LocalDaemon implements PixConServer {

	private PixelController pixelController;
	
	public LocalDaemon(CallbackMessageInterface<String> msgHandler) {
		pixelController = PixelControllerFactory.initialize(msgHandler);
	}

	@Override
	public void startCore() {
		pixelController.start();
	}

	@Override
	public String getVersion() {
		return pixelController.getVersion();
	}

	@Override
	public ApplicationConfigurationHelper getConfig() {
		return pixelController.getConfig();
	}

	@Override
	public boolean isInitialized() {		
		return pixelController.isInitialized();
	}

	@Override
	public int[] getVisualBuffer(int nr) {
		return pixelController.getOutput().getBufferForScreen(nr, true);
	}

	@Override
	public MatrixData getMatrixData() {
		return pixelController.getMatrix();
	}

	@Override
	public int getCurrentPreset() {
		return pixelController.getPresetService().getSelectedPreset();
	}

	@Override
	public PresetSettings getCurrentPresetSettings() {
		return pixelController.getPresetService().getSelectedPresetSettings();
	}

	@Override
	public void updateNeededTimeForMatrixEmulator(long t) {
		pixelController.getPixConStat().trackTime(TimeMeasureItemGlobal.MATRIX_EMULATOR_WINDOW, t);		
	}

	@Override
	public void updateNeededTimeForInternalWindow(long t) {		
		pixelController.getPixConStat().trackTime(TimeMeasureItemGlobal.DEBUG_WINDOW, t);
	}

	@Override
	public void sendMessage(String[] msg) {
        MessageProcessor.INSTANCE.processMsg(msg, true, null);
	}

	@Override
	public IOutput getOutput() {
		return pixelController.getOutput();
	}

	@Override
	public float getCurrentFps() {		
		return pixelController.getFps();
	}

	@Override
	public long getServerStartTime() {		
		return pixelController.getPixConStat().getStartTime();
	}

	@Override
	public long getRecievedOscPackets() {
		return pixelController.getPixConStat().getRecievedOscPakets();
	}

	@Override
	public long getRecievedOscBytes() {
		return pixelController.getPixConStat().getRecievedOscBytes();
	}

	@Override
	public ISound getSoundImplementation() {
		return pixelController.getSoundImplementation();
	}

	@Override
	public int getNrOfVisuals() {
		return pixelController.getConfig().getNrOfScreens()+pixelController.getConfig().getNrOfAdditionalVisuals();
	}

	@Override
	public long getFrameCount() {		
		return pixelController.getProcessedFrames();
	}

	@Override
	public void refreshGuiState() {
		pixelController.refreshGuiState();
	}

	@Override
	public void registerObserver(Observer o) {
		pixelController.registerObserver(o);		
	}

	@Override
	public List<ColorSet> getColorSets() {
		return pixelController.getColorSets();
	}

	@Override
	public List<OutputMapping> getAllOutputMappings() {
		return pixelController.getAllOutputMappings();
	}

}
