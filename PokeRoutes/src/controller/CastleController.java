package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.BonusCard;
import model.CardForm;
import model.CardType;
import model.Castle;
import model.CastleCard;
import model.CastleForm;
import model.CountBonusCard;
import model.Direction;
import model.FormBonusCard;
import model.HeightBonusCard;
import model.PlayedCardHolder;
import model.Position;
import model.Rotation;
import model.SizeBonusCard;

public class CastleController {

	private GameController gameController;

	public CastleController(GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * Tests if the CastleCard of card can be removed without destroying the
	 * castle
	 * 
	 * @param card
	 *            PlayedCardHolder that contains the CastleCard which will be
	 *            temporally erased from the castle
	 * @return If the CastleCard cannot be removed, return false otherwise true
	 */
	public boolean canRemove(PlayedCardHolder card) {
		if (card.getCastleCards().size() == 1) {
			Castle castle = card.getCastle();
			CastleCard castleCard = card.getTopCard();
			Position pos = card.getPosition();
			
			castle.removePlayedCardHolder(pos);
			boolean result = isCastleConnected(castle);
			castle.addPlayedCardHolder(pos, card);
			
			return result;
		} else {
			return canRemoveTower(card);
		}
	}

	/**
	 * Converts castle into a 2-dimensional Array
	 * 
	 * @param castle
	 *            Castle that will be converted into a 2-dimensional Array
	 * @return Resulting 2-dimensional Array from castle
	 */
	public int[][] convertToMatrix(Castle castle) {
		Collection<PlayedCardHolder> cards = castle.getPlayedCards().values();
		int maxX = 0, minX = 0, maxY = 0, minY = 0;
		for (PlayedCardHolder current : cards) {
			Position pos = current.getPosition();
			maxX = Math.max(pos.getX(), maxX);
			minX = Math.min(pos.getX(), minX);
			maxY = Math.max(pos.getY(), maxY);
			minY = Math.min(pos.getY(), minY);
		}
		int[][] result = new int[maxX - minX + 1][maxY - minY + 1];
		for (PlayedCardHolder current : cards) {
			result[current.getPosition().getX() - minX][current.getPosition().getY() - minY] = 1;
		}
		return result;
	}

	/**
	 * Converts castle into a square-matrix (2-dimensional Array) and compares
	 * it with form
	 * 
	 * @param castle
	 *            Castle that will be compared to form
	 * @param form
	 *            Given form that should be fulfilled
	 * @return If castle fits into form, return true otherwise false
	 */
	public boolean checkPattern(Castle castle, CastleForm form) {
		int[][] castleM = MatrixUtility.squareMatrix(convertToMatrix(castle));
		if (MatrixUtility.compareRotateMatrix(form.getMatrix(), castleM)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the current BonusCards from the table and checks whether castle
	 * fulfills their conditions or not
	 * 
	 * @param castle
	 *            Castle that maybe fulfills the conditions of given BonusCards
	 * @return Returns an ArrayList of the fulfilled BonusCards
	 * @throws Exception
	 *             Throws an Exception, if castle is not completed
	 */
	public List<BonusCard> listAvailableBonusCards(Castle castle)  {
		if (castle.isCompleted()) {
			ArrayList<BonusCard> bonusCards = gameController.getTable().getBonusCards();
			ArrayList<BonusCard> availableBonusCards = new ArrayList<BonusCard>();
			CountBonusCard maxB = null;
			CountBonusCard maxS = null;
			CountBonusCard maxC = null;
			for (BonusCard current : bonusCards) {
				if (current instanceof SizeBonusCard
						&& ((SizeBonusCard) current).getCurrentBiggest() < castle.getSize()) {
					((SizeBonusCard) current).setCurrentBiggest(castle.getSize());
					availableBonusCards.add(current);
				} else if (current instanceof HeightBonusCard
						&& ((HeightBonusCard) current).getCurrentHighest() < castle.getHeight()) {
					((HeightBonusCard) current).setCurrentHighest(castle.getHeight());
					availableBonusCards.add(current);
				} else if (current instanceof CountBonusCard && ((CountBonusCard) current)
						.getRequired() <= countCardType(castle, ((CountBonusCard) current).getType())) {
					CardType type = ((CountBonusCard) current).getType();
					if (type == CardType.BUCKET
							&& (maxB == null || maxB.getRequired() < ((CountBonusCard) current).getRequired())) {
						maxB = ((CountBonusCard) current);
					} else if (type == CardType.SEAGULL
							&& (maxS == null || maxS.getRequired() < ((CountBonusCard) current).getRequired())) {
						maxS = ((CountBonusCard) current);
					} else if (type == CardType.CRAB
							&& (maxC == null || maxC.getRequired() < ((CountBonusCard) current).getRequired())) {
						maxC = ((CountBonusCard) current);
					}
				} else if (current instanceof FormBonusCard
						&& checkPattern(castle, ((FormBonusCard) current).getForm())) {
					availableBonusCards.add(current);
				}
			}
			if (maxB != null)
				availableBonusCards.add(maxB);
			if (maxS != null)
				availableBonusCards.add(maxS);
			if (maxC != null)
				availableBonusCards.add(maxC);
			return availableBonusCards;
		} else {
			throw new RuntimeException("Castle not completed to claim bonus cards");
		}
	}

	/**
	 * Counts the cards of a type determined by cardType in castle
	 * 
	 * @param castle
	 *            Castle contains the counted cards
	 * @param cardType
	 *            CarType to be counted
	 * @return The number of counted cards
	 */
	public int countCardType(Castle castle, CardType cardType) {
		int count = 0;
		Collection<PlayedCardHolder> cards = castle.getPlayedCards().values();
		for (PlayedCardHolder card : cards) {
			if (card.getTopCard().getType() == cardType)
				count++;
		}
		return count;
	}

	/**
	 * Lays newCrad with a rotation alongside target at a specific direction, if
	 * possible
	 * 
	 * @param target
	 *            The target where newCard is to be laid alongside
	 * @param direction
	 *            The direction where newCard is to be laid alongside
	 *            (top/right/bottom/left)
	 * @param newCard
	 *            The CastleCard that will be laid
	 * @param rotation
	 *            The rotation of newCard (north(east/south/west)
	 * @throws Exception
	 *             Throws an Exception, if newCard does not fit
	 */
	public void addToCastle(PlayedCardHolder target, Direction direction, CastleCard newCard, Rotation rotation)
			throws Exception {
		Castle castle = target.getCastle();
		Position targetPosition = target.getPosition();
		newCard.setRotation(rotation);
		boolean isAdded = false;
		switch (direction) {
		case TOP:
			if (fitsInCastle(castle, newCard, targetPosition.add(0, -1)) && target.getNorthCard() == null) {
				castle.addCard(targetPosition.add(0, -1), newCard);
				isAdded = true;
			}
			break;
		case RIGHT:
			if (fitsInCastle(castle, newCard, targetPosition.add(1, 0)) && target.getEastCard() == null) {
				castle.addCard(targetPosition.add(1, 0), newCard);
				isAdded = true;
			}
			break;
		case BOTTOM:
			if (fitsInCastle(castle, newCard, targetPosition.add(0, 1)) && target.getSouthCard() == null) {
				castle.addCard(targetPosition.add(0, 1), newCard);
				isAdded = true;
			}
			break;
		case LEFT:
			if (fitsInCastle(castle, newCard, targetPosition.add(-1, 0)) && target.getWestCard() == null) {
				castle.addCard(targetPosition.add(-1, 0), newCard);
				isAdded = true;
			}
			break;
		case UPPER:
			if (fitsInCastle(castle, newCard, targetPosition) && newCard.getType() == CardType.SEAGULL) {
				castle.addCard(targetPosition, newCard);
				isAdded = true;
			}
			break;
		default:
			throw new Exception("Karte passt nicht!");
		}
		
		if (!isAdded)
			throw new Exception("Karte passt nicht!");
		

		isComplete(castle);
	}

	/**
	 * Checks whether castle is completed or not, which means there should be no
	 * open connections in castle
	 * 
	 * @param castle
	 *            Caste that maybe completed
	 * @return Returns true, if the castle is completed otherwise false
	 * @throws Exception
	 */
	public void isComplete(Castle castle) {
		int count = 0;
		for (PlayedCardHolder cardHolder : castle.getPlayedCards().values()) {
			CastleCard card = cardHolder.getTopCard();
			Rotation rotation = card.getRotation();
			CardForm form = card.getCardForm();
			if ((!form.hasLeftConnection(rotation) || cardHolder.getWestCard() != null)
					&& (!form.hasRightConnection(rotation) || cardHolder.getEastCard() != null)
					&& (!form.hasTopConnection(rotation) || cardHolder.getNorthCard() != null)
					&& (!form.hasBottomConnection(rotation) || cardHolder.getSouthCard() != null)) {
				count++;
			}
		}
		
		castle.setCompleted(count == castle.getSize());
		

		if(castle.isCompleted()) {
			gameController.getPointsController().addPoints(castle);
		}
	}

	/**
	 * Tests if card fits in pos of castle, by checking the surrounding
	 * connections
	 * 
	 * @param castle
	 *            Castle in which card may fits
	 * @param card
	 *            CastleCard which may fits in castle
	 * @param pos
	 *            Specific position in which card may fit
	 * @return Returns true, if card fits in pos of castle otherwise false
	 */
	public boolean fitsInCastle(Castle castle, CastleCard card, Position pos) {
		if (castle.getCardByPosition(pos) != null && card.getType() != CardType.SEAGULL) {
			return false;
		}
		PlayedCardHolder topHolder = castle.getCardByPosition(pos.add(0, -1));
		PlayedCardHolder rightHolder = castle.getCardByPosition(pos.add(1, 0));
		PlayedCardHolder bottomHolder = castle.getCardByPosition(pos.add(0, 1));
		PlayedCardHolder leftHolder = castle.getCardByPosition(pos.add(-1, 0));
		Rotation cardRotation = card.getRotation();
		CardForm cardForm = card.getCardForm();

		if (topHolder != null) {
			CastleCard top = topHolder.getTopCard();
			CardForm topForm = top.getCardForm();
			Rotation topRotation = top.getRotation();
			boolean hasTop = cardForm.hasTopConnection(cardRotation);
			boolean topHasBottom = topForm.hasBottomConnection(topRotation);
			if ((!hasTop && topHasBottom) || (hasTop && !topHasBottom)) {
				return false;
			}
		}
		if (rightHolder != null) {
			CastleCard right = rightHolder.getTopCard();
			Rotation rightRotation = right.getRotation();
			CardForm rightForm = right.getCardForm();
			boolean hasRight = cardForm.hasRightConnection(cardRotation);
			boolean rightHasLeft = rightForm.hasLeftConnection(rightRotation);
			if ((!hasRight && rightHasLeft) || (hasRight && !rightHasLeft)) {
				return false;
			}
		}

		if (leftHolder != null) {
			CastleCard left = leftHolder.getTopCard();
			Rotation leftRotation = left.getRotation();
			CardForm leftForm = left.getCardForm();
			boolean hasLeft = cardForm.hasLeftConnection(cardRotation);
			boolean leftHasRight = leftForm.hasRightConnection(leftRotation);
			if ((!hasLeft && leftHasRight) || (hasLeft && !leftHasRight)) {
				return false;
			}
		}

		if (bottomHolder != null) {
			CastleCard bottom = bottomHolder.getTopCard();
			Rotation bottomRotation = bottom.getRotation();
			CardForm bottomForm = bottom.getCardForm();
			boolean hasBottom = cardForm.hasBottomConnection(cardRotation);
			boolean bottomHasTop = bottomForm.hasTopConnection(bottomRotation);
			if ((!hasBottom && bottomHasTop) || (hasBottom && !bottomHasTop)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if castle is connected, which means that you can reach every card
	 * from every other card
	 * 
	 * @param castle
	 *            Castle that may be connected
	 * @return Returns true, if castle is connected otherwise false
	 */
	private boolean isCastleConnected(Castle castle) {
		// TODO: Maybe error handling
		if (castle == null)
			return true;
		Collection<PlayedCardHolder> cards = castle.getPlayedCards().values();
		ArrayList<PlayedCardHolder> grey = new ArrayList<>();
		PlayedCardHolder start = null;
		for (PlayedCardHolder playedCardHolder : cards) {
			start = playedCardHolder;
			break;
		}
		Queue<PlayedCardHolder> queue = new LinkedList<>();
		queue.add(start);
		grey.add(start);
		while (!queue.isEmpty()) {
			PlayedCardHolder current = queue.remove();
			CastleCard card = current.getTopCard();
			Rotation currentRotation = card.getRotation();
			CardForm currentForm = card.getCardForm();
			if (currentForm.hasTopConnection(currentRotation) && current.getNorthCard() != null) {
				PlayedCardHolder north = current.getNorthCard();
				if (!grey.contains(north)) {
					queue.add(north);
					grey.add(north);
				}
			}
			if (currentForm.hasRightConnection(currentRotation) && current.getEastCard() != null) {
				PlayedCardHolder east = current.getEastCard();
				if (!grey.contains(east)) {
					queue.add(east);
					grey.add(east);
				}
			}
			if (currentForm.hasBottomConnection(currentRotation) && current.getSouthCard() != null) {
				PlayedCardHolder south = current.getSouthCard();
				if (!grey.contains(south)) {
					queue.add(south);
					grey.add(south);
				}
			}
			if (currentForm.hasLeftConnection(currentRotation) && current.getWestCard() != null) {
				PlayedCardHolder west = current.getWestCard();
				if (!grey.contains(west)) {
					queue.add(west);
					grey.add(west);
				}
			}
		}
		return grey.size() == cards.size();
	}

	/**
	 * Test if the top CastleCard of the stack in card can be removed without
	 * destroying the castle, by checking if the CastleCard below fits in the
	 * castle
	 * 
	 * @param card
	 *            PlayedCardHolder that contains the stack
	 * @return If the top CastleCard cannot be removed, return false otherwise
	 *         true
	 */
	private boolean canRemoveTower(PlayedCardHolder card) {
		Castle castle = card.getCastle();
		CastleCard castleCard = card.getCastleCards().pop();
		Position pos = card.getPosition();
		
		boolean result = fitsInCastle(castle, card.getCastleCards().peek(), pos);
		card.getCastleCards().push(castleCard);
		return result;
	}
}
