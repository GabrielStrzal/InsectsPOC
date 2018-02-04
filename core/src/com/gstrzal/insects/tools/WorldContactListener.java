package com.gstrzal.insects.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gstrzal.insects.assets.AssetPaths;

/**
 * Created by lelo on 31/01/18.
 */

public class WorldContactListener implements ContactListener {

    private int onGrounds = 0;
    public boolean isOnGrounds(){
        if(onGrounds == 1) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(isContact(contact, "base", AssetPaths.MAP_BLOCKS)){
            System.out.println("Contact with Base");
            onGrounds++;
        }

        if(isContact(contact, "InsectBody", AssetPaths.MAP_COINS)){
            System.out.println("Contact with Coin");
        }
        if(isContact(contact, "InsectBody", AssetPaths.MAP_END)){
            System.out.println("Contact with Level End");
        }



//        if(fixA.getUserData() == "base" || fixB.getUserData() == "base"){
//            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
//            Fixture object = head == fixA ? fixB : fixA;
//            System.out.println("Contact with Base");
//            onGrounds++;
//
//        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(isContact(contact, "base", AssetPaths.MAP_BLOCKS)){
            System.out.println("Ended contact with Base");
            onGrounds--;
        }
        if(isContact(contact, "InsectBody", AssetPaths.MAP_COINS)){
            System.out.println("Ended contact with Coin");
        }
        if(isContact(contact, "InsectBody", AssetPaths.MAP_END)){
            System.out.println("Ended contact with Level End");
        }

//        if(fixA.getUserData() == "base" || fixB.getUserData() == "base"){
//            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
//            Fixture object = head == fixA ? fixB : fixA;
//            System.out.println("Ended contact with Base");
//            onGrounds--;
//
//        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean isContact(Contact contact, String a, String b){
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if((fixA.getUserData() == a && fixB.getUserData() == b) || (fixA.getUserData() == b && fixB.getUserData() == a)){
            return true;
        }else{
            return false;
        }
    }
}