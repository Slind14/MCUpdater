package org.mcupdater;

import org.eclipse.swt.widgets.Display;

public class MCUClientTracker implements TrackerListener {

	
	private Display display;
	private MCUProgress progress;

	public MCUClientTracker(Display display, MCUProgress progress) {
		this.display = display;
		this.progress = progress;
	}

	@Override
	public void onQueueFinished(final DownloadQueue queue) {
		MainShell.getInstance().log(queue.getName() + ": Finished!");
		display.syncExec(new Runnable(){
			@Override
			public void run() {
				if (progress == null || progress.isDisposed()) { return; }
				progress.updateProgress(queue.getName(),queue.getParent(),1.0F,queue.getTotalFileCount(),queue.getSuccessFileCount());
			}
		});

	}

	@Override
	public void onQueueProgress(final DownloadQueue queue) {
		display.syncExec(new Runnable(){
			@Override
			public void run() {
				if (progress == null || progress.isDisposed()) { return; }
				progress.updateProgress(queue.getName(),queue.getParent(),queue.getProgress(),queue.getTotalFileCount(),queue.getSuccessFileCount());
			}
		});
	}

	@Override
	public void printMessage(String msg) {
		MainShell.getInstance().log(msg);				
	}

}
