package viewcontroller;

import javafx.scene.image.Image;

public class FrameSet {
	private final Image[] images;
	private final int framesPerMs;
	
	public FrameSet(Image[] images, int framesPerMs) {
		this.images = images;
		this.framesPerMs = framesPerMs;
	}
	
	/**
	 * @return the framesPerMs
	 */
	public int getFramesPerMs() {
		return framesPerMs;
	}
	/**
	 * @return the images
	 */
	public Image[] getImages() {
		return images;
	}

}
