package viewcontroller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.HashMap;

import javafx.scene.image.Image;
import model.CardForm;
import model.CardType;
import model.CastleForm;
public class ResourceController {
	private static final String AVATAR_PATH = "avatar/%d.png",
			WAY_CROSS_PATH = "way/4-cross.png",
			WAY_END_PATH = "way/end.png",
			WAY_T_CROSS_PATH = "way/t_cross.png",
			WAY_STRAIGHT = "way/straight.png",
			WAY_CURVE = "way/curve.png",
			BONUS_COUNT = "bonus/count/%s%d.png",
			BONUS_FORM ="bonus/form/%s.png",
			BONUS_TOWER_HEIGHT = "bonus/size/tower.png",
			BONUS_CASTLE_SIZE = "bonus/size/castle.png",
			CARD_COMBAT ="combat/%s.png",
			CARD_BIGWAVE = "bigwave.png",
			CARD_BACK = "back.png",
			CARD_FRAME_COUNT = "cardtype/%s/count.txt",
			CARD_FRAME_PER_MS = "cardtype/%s/frames.txt",
			CARD_FRAMES = "cardtype/%s/frames/out%05d.png",
			
			ICON = "menu/pokeball_icon.png";
	
	private Image[] avatars;
	private Image icon;
	private Image bigWave, back;
	private HashMap<CardForm, Image> wayCards;
	private HashMap<CardType, Image> bonusCards3,
		bonusCards4,
		bonusCards5;
	private HashMap<CastleForm, Image> bonusForm;
	private Image bonusCastleSize, bonusTowerHeight;
	private HashMap<CardType, Image> combatCard;
	private HashMap<CardType, FrameSet> cardFrames;
	
	
	private URL baseUrl;
	
	public ResourceController(URL baseUrl) {
		this.baseUrl = baseUrl;
		avatars = new Image[10];
		wayCards = new HashMap<CardForm, Image>();
		
		bonusCards3 = new HashMap<CardType, Image>();
		bonusCards4 = new HashMap<CardType, Image>();
		bonusCards5 = new HashMap<CardType, Image>();
		
		bonusForm = new HashMap<CastleForm, Image>();
		
		combatCard = new HashMap<CardType, Image>();
		
		cardFrames = new HashMap<CardType, FrameSet>();
	}
	
	private URL concatResource(String extraPath) 
			throws URISyntaxException, MalformedURLException {
		URI uri = baseUrl.toURI();
		String newPath = uri.getPath() + '/' + extraPath;
		URI newUri = uri.resolve(newPath);
		return newUri.toURL();
	}
	
	private Image loadImg(String path) throws URISyntaxException, IOException {
		String filePath = concatResource(path).toString();
		Image img = new Image(filePath);
		
		if(!img.isError())
			return img;
		
		throw new IOException("Image does not exist: " + filePath);
	}
	
	private String loadText(String pathStr) throws URISyntaxException, IOException {
		Path path = Paths.get(concatResource(pathStr).toURI());
		return Files.readAllLines(path).get(0);
	}
	
	private void loadAvatars() throws URISyntaxException, IOException {
		for(int i = 0; i < 10; i++) {
			avatars[i] = loadImg(String.format(AVATAR_PATH, i+1));
		}
	}
	
	private void loadWayCards() throws URISyntaxException, IOException {
		wayCards.put(CardForm.CROSS, loadImg(WAY_CROSS_PATH));
		wayCards.put(CardForm.END, loadImg(WAY_END_PATH));
		wayCards.put(CardForm.T_CROSS, loadImg(WAY_T_CROSS_PATH));
		wayCards.put(CardForm.STRAIGHT, loadImg(WAY_STRAIGHT));
		wayCards.put(CardForm.CURVE, loadImg(WAY_CURVE));
	}
	
	private void loadBonusCount(CardType cur, String curStr) throws URISyntaxException, IOException {
		bonusCards3.put(cur, loadImg(String.format(BONUS_COUNT, curStr, 3)));
		bonusCards4.put(cur, loadImg(String.format(BONUS_COUNT, curStr, 4)));
		bonusCards5.put(cur, loadImg(String.format(BONUS_COUNT, curStr, 5)));
	}
	
	private void loadBonusForm() throws URISyntaxException, IOException{
		bonusForm.put(CastleForm.AXE, loadImg(String.format(BONUS_FORM, "axe")));
		bonusForm.put(CastleForm.BARRACK, loadImg(String.format(BONUS_FORM, "barack")));
		bonusForm.put(CastleForm.CACTUS, loadImg(String.format(BONUS_FORM, "cactus")));
		bonusForm.put(CastleForm.GOLDFISH, loadImg(String.format(BONUS_FORM, "goldfish")));
		bonusForm.put(CastleForm.KEEP, loadImg(String.format(BONUS_FORM, "keep")));
		bonusForm.put(CastleForm.MONASTERY, loadImg(String.format(BONUS_FORM, "monastery")));
		bonusForm.put(CastleForm.PEACOCK, loadImg(String.format(BONUS_FORM, "peacock")));
		bonusForm.put(CastleForm.STAIRWAY, loadImg(String.format(BONUS_FORM, "stairway")));
		bonusForm.put(CastleForm.WORM, loadImg(String.format(BONUS_FORM, "worm")));
	}
	
	private void loadBonusSize() throws URISyntaxException, IOException {
		bonusCastleSize = loadImg(BONUS_CASTLE_SIZE);
		bonusTowerHeight = loadImg(BONUS_TOWER_HEIGHT);
	}
	
	private void loadCombatCard() throws URISyntaxException, IOException{
		combatCard.put(CardType.BUCKET, loadImg(String.format(CARD_COMBAT, "bucket")));
		combatCard.put(CardType.CRAB, loadImg(String.format(CARD_COMBAT, "crab")));
		combatCard.put(CardType.SEAGULL, loadImg(String.format(CARD_COMBAT, "seagull")));
	}
	
	private void loadFrameSet(CardType cardType, String cardTypeStr) throws URISyntaxException, IOException {
		String frameCountStr = loadText(String.format(CARD_FRAME_COUNT, cardTypeStr)); 
		String framesPerMsStr = loadText(String.format(CARD_FRAME_PER_MS, cardTypeStr)); 
		
		int frameCount = Integer.parseInt(frameCountStr);
		int framesPerMs = Integer.parseInt(framesPerMsStr);
		
		Image[] img = new Image[frameCount];
		for(int i = 0; i < (frameCount); i++) {
			img[i] = loadImg(String.format(CARD_FRAMES, cardTypeStr, i));
		}
		
		cardFrames.put(cardType, new FrameSet(img, framesPerMs));
	}
	
	public void load() throws URISyntaxException, IOException {
		loadAvatars();
		loadIcon();
	}
	
	public void loadIcon() throws URISyntaxException, IOException {
		this.icon = loadImg(ICON);
	}
	
	public void loadGame()  throws URISyntaxException, IOException {
		this.bigWave = loadImg(CARD_BIGWAVE);
		this.back = loadImg(CARD_BACK);
		loadWayCards();
		
		loadBonusCount(CardType.BUCKET, "bucket");
		loadBonusCount(CardType.SEAGULL, "seagull");
		loadBonusCount(CardType.CRAB, "crab");
		loadBonusForm();
		loadBonusSize();
		loadCombatCard();
		loadFrameSet(CardType.BUCKET, "bucket");
		loadFrameSet(CardType.SEAGULL, "seagull");
		loadFrameSet(CardType.CRAB, "crab");
		
	}
	
	public Image getWayCard(CardForm cardForm) {
		return wayCards.get(cardForm);
	}
	
	public Image getFormBonusCard(CastleForm castleForm) {
		return bonusForm.get(castleForm);
	}
	
	public Image getCombatCard(CardType cardType) {
		return combatCard.get(cardType);
	}
	
	public FrameSet getCardTypeFrame(CardType cardType) {
		return cardFrames.get(cardType);
	}
	
	public Image getAvatar(int id) {
		return avatars[id];
	}

	/**
	 * @return the bigWave
	 */
	public Image getBigWave() {
		return bigWave;
	}

	/**
	 * @return the back
	 */
	public Image getBack() {
		return back;
	}

	/**
	 * @return the bonusCastleSize
	 */
	public Image getBonusCastleSize() {
		return bonusCastleSize;
	}

	/**
	 * @return the bonusTowerHeight
	 */
	public Image getBonusTowerHeight() {
		return bonusTowerHeight;
	}
	
	public Image getCountBonusCard(CardType cardType, int count) {
		Image img;
		switch(count) {
		case 3:
			img = bonusCards3.get(cardType);
			break;
		case 4:
			img = bonusCards4.get(cardType);
			break;
		case 5:
			img = bonusCards5.get(cardType);
			break;
		default:
			throw new InvalidParameterException("Count should be either 3,4 or 5");
		}
		
		return img;
	}

	public Image getIcon() {
		return icon;
	}
}
