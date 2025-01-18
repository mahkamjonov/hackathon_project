package org.example.repo;

import org.example.entity.CardEntity;
import org.example.entity.TransferEntity;

import java.util.ArrayList;

public class DataBase {

    private ArrayList<CardEntity> cardList = new ArrayList<>();

    private ArrayList<TransferEntity> transferList = new ArrayList<>();


    public ArrayList<TransferEntity> getMonitoring( Long userId ) {
        ArrayList<TransferEntity> res = new ArrayList<>();
        for ( TransferEntity entity: transferList ){
            if ( entity.getUserId().equals( userId ) ) {
                res.add( entity );
            }
        }
        return res;
    }

    public void setTransfer(TransferEntity entity){
        transferList.add(entity);
    }

    public CardEntity getCardByCardNumber( String number ) {

        for ( CardEntity entity: cardList ){
            if ( entity.getNumber().equals(number) ) {
                return entity;
            }
        }

        return null;
    }

    public CardEntity getCardByUserId( Long id ) {

        for ( CardEntity entity: cardList ) {
            if ( entity.getUserId().equals(id) ) {
                return entity;
            }
        }
        return null;
    }

    public void saveTransfer( CardEntity entity ) {
        for ( CardEntity entity1: cardList ) {
            if ( entity.getId().equals( entity1.getId() ) ) {
                entity1 = entity;
            }
        }
    }

    public void setCardsToData(CardEntity card) {
        if (card.getId() != null && card.getUserId() != null && card.getNumber() != null){
            cardList.add(card);
        }
    }

    public void addCard() {
        CardEntity card = new CardEntity(1L, 1L, 1000000, "8600490523819969", "Bekkulbekov", "Abdulloh");
        CardEntity card2 = new CardEntity(2L, 2L, 1000000, "8600338125911000", "Ravshanov", "Javohir");
        CardEntity card3 = new CardEntity(3L, 3L, 1000000, "8600444444444444", "Zokirjonov", "Yusufjon");
        CardEntity card4 = new CardEntity(4L, 4L, 1000000, "8600148869965667", "Husanov", "Abdulbosit");
        CardEntity card5 = new CardEntity(5L, 5L, 1000000, "8600000000000000", "Kabirov", "Azizjon");
        CardEntity card6 = new CardEntity(6L, 6L, 1000000, "8600777777777777", "Abdullayev", "Anvar");
        CardEntity card7 = new CardEntity(7L, 7L, 1000000, "8600160127023803", "Ibrohim", "Mahkamjonov");
        CardEntity card8 = new CardEntity(8L, 8L, 1000000, "8600860086008600", "Abdumalikov", "Nuraziz");

        setCardsToData(card);
        setCardsToData(card2);
        setCardsToData(card3);
        setCardsToData(card4);
        setCardsToData(card5);
        setCardsToData(card6);
        setCardsToData(card7);
        setCardsToData(card8);

    }

    public boolean findCard( String cardNumber, Long userId ) {

        for ( CardEntity entity: cardList ) {
            if ( entity.getNumber().equals( cardNumber ) && entity.getUserId() < 10L ) {
                entity.setUserId( userId );
                return true;
            }
        }

        return false;
    }


    public double getBalance( Long userId ) {

        for ( CardEntity entity: cardList ) {
            if ( entity.getUserId().equals( userId ) ) {
                return entity.getBalance();
            }
        }

        return -1;
    }


}
