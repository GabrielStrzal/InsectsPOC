package com.gstrzal.insects.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gstrzal.insects.Insects;
import com.gstrzal.insects.config.Constants;
import com.gstrzal.insects.entity.Flower;
import com.gstrzal.insects.entity.LevelEndBlock;
import com.gstrzal.insects.entity.LevelSwitch;
import com.gstrzal.insects.entity.PushBox;

/**
 * Created by Gabriel on 03/09/2017.
 */

public class B2WorldCreator {

    public Array<Flower> flowers;
    public Array<PushBox> pushBoxes;
    public LevelSwitch levelSwitch;
    public LevelEndBlock levelEndBlock;
    private final static float circleRadius = 32f;

    public B2WorldCreator(World world, TiledMap map, Insects insects){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        flowers = new Array<Flower>();
        pushBoxes = new Array<PushBox>();

        //Blocks
        if(map.getLayers().get(Constants.MAP_BLOCKS) != null)
        for (MapObject object : map.getLayers().get(Constants.MAP_BLOCKS).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Insects.PPM, (rect.getY() + rect.getHeight() / 2) / Insects.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Insects.PPM, (rect.getHeight() / 2) / Insects.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Insects.BRICK_BIT;
            fdef.filter.maskBits = Insects.INSECT_BIT | Insects.BASE_BIT | Insects.CHANGE_INSECT_BIT | Insects.PUSH_BLOCK_BIT;
            fdef.friction = 0;
            body.createFixture(fdef).setUserData(Constants.MAP_BLOCKS);
        }

        //Pass Blocks
        if(map.getLayers().get(Constants.MAP_PASS_BLOCKS) != null)
        for (MapObject object : map.getLayers().get(Constants.MAP_PASS_BLOCKS).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Insects.PPM, (rect.getY() + rect.getHeight() / 2) / Insects.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Insects.PPM, (rect.getHeight() / 2) / Insects.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Insects.PASS_BLOCK_BIT;
            fdef.filter.maskBits = Insects.INSECT_BIT | Insects.BASE_BIT;
            fdef.friction = 0;
            body.createFixture(fdef).setUserData(Constants.MAP_PASS_BLOCKS);
        }


        //Flowers
        if(map.getLayers().get(Constants.MAP_FLOWERS) != null)
        for (MapObject object : map.getLayers().get(Constants.MAP_FLOWERS).getObjects()) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(ellipse.x / Insects.PPM + (ellipse.width / 2) / Insects.PPM,
                    ellipse.y/ Insects.PPM + (ellipse.height / 2) / Insects.PPM);
            CircleShape circleShape =new CircleShape();
            circleShape.setRadius(circleRadius/Insects.PPM);
            fdef.shape = circleShape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Insects.FLOWER_BIT;
            fdef.filter.maskBits = Insects.INSECT_BIT ;
            Body flowerBody = world.createBody(bdef);
            flowerBody.createFixture(fdef).setUserData(Constants.MAP_FLOWERS);
            Flower f = new Flower(flowerBody, insects);
            flowerBody.setUserData(f);
            flowers.add(f);

        }

        //Level End
        if(map.getLayers().get(Constants.MAP_END) != null)
        for (MapObject object : map.getLayers().get(Constants.MAP_END).getObjects()) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(ellipse.x / Insects.PPM + (ellipse.width / 2) / Insects.PPM,
                    ellipse.y/ Insects.PPM + (ellipse.height / 2) / Insects.PPM);
            CircleShape circleShape =new CircleShape();
            circleShape.setRadius(circleRadius/Insects.PPM);
            fdef.shape = circleShape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Insects.LEVEL_END_BIT;
            fdef.filter.maskBits = Insects.INSECT_BIT;
            Body endBody = world.createBody(bdef);
            endBody.createFixture(fdef).setUserData(Constants.MAP_END);
        }

        //Damage
        if(map.getLayers().get(Constants.MAP_DAMAGE) != null)
        for (MapObject object : map.getLayers().get(Constants.MAP_DAMAGE).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Insects.PPM, (rect.getY() + rect.getHeight() / 2) / Insects.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Insects.PPM, (rect.getHeight() / 2) / Insects.PPM);
            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Insects.DAMAGE_BIT;
            fdef.filter.maskBits = Insects.INSECT_BIT | Insects.BASE_BIT;
            fdef.friction = 0;
            body.createFixture(fdef).setUserData(Constants.MAP_DAMAGE);
        }


        //Slope
        BodyDef bdef2 = new BodyDef();
        FixtureDef fdef2 = new FixtureDef();
        Body body2;
        if(map.getLayers().get(Constants.MAP_SLOPE) != null)
            for (MapObject object : map.getLayers().get(Constants.MAP_SLOPE).getObjects().getByType(PolygonMapObject.class)) {
                Polygon polygonObject = ((PolygonMapObject) object).getPolygon();
                bdef2.type = BodyDef.BodyType.StaticBody;
                bdef2.position.set(polygonObject.getOriginX()/Insects.PPM, polygonObject.getOriginY()/Insects.PPM);
                PolygonShape polygon = new PolygonShape();
                float[] vertices = polygonObject.getTransformedVertices();
                float[] worldVertices = new float[vertices.length];

                for (int i = 0; i < vertices.length; ++i) {
                    worldVertices[i] = vertices[i] / Insects.PPM;
                }
                polygon.set(worldVertices);
                fdef2.shape = polygon;
                fdef2.filter.categoryBits = Insects.SLOPE_BIT;
                fdef2.filter.maskBits = Insects.INSECT_BIT | Insects.BASE_BIT | Insects.CHANGE_INSECT_BIT;
                body2 = world.createBody(bdef2);
                body2.createFixture(fdef2).setUserData(Constants.MAP_SLOPE);
            }


        //Push Block
        BodyDef bdef3 = new BodyDef();
        FixtureDef fdef3 = new FixtureDef();
        Body body3;
        if(map.getLayers().get(Constants.MAP_PUSH_BLOCKS) != null)
            for (MapObject object : map.getLayers().get(Constants.MAP_PUSH_BLOCKS).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                bdef3.type = BodyDef.BodyType.DynamicBody;
                bdef3.position.set((rect.getX() + rect.getWidth() / 2) / Insects.PPM, (rect.getY() + rect.getHeight() / 2) / Insects.PPM);
                body3 = world.createBody(bdef3);
                shape.setAsBox((rect.getWidth() / 2) / Insects.PPM, (rect.getHeight() / 2) / Insects.PPM);
                fdef3.shape = shape;
                fdef3.filter.categoryBits = Insects.PUSH_BLOCK_BIT;
                fdef3.filter.maskBits = Insects.INSECT_BIT | Insects.BASE_BIT | Insects.BRICK_BIT | Insects.CHANGE_INSECT_BIT;
                MassData massData = new MassData();
                massData.mass = 10000;
                body3.setMassData(massData);
                body3.createFixture(fdef3).setUserData(Constants.MAP_PUSH_BLOCKS);
                PushBox pushBox = new PushBox(body3, insects);
                body3.setUserData(pushBox);
                pushBoxes.add(pushBox);
            }

        //Switch
        BodyDef switchBdef = new BodyDef();
        FixtureDef switchfdef = new FixtureDef();
        Body switchBody;
        if(map.getLayers().get(Constants.MAP_SWITCH) != null)
            for (MapObject object : map.getLayers().get(Constants.MAP_SWITCH).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                switchBdef.type = BodyDef.BodyType.StaticBody;
                switchBdef.position.set((rect.getX() + rect.getWidth() / 2) / Insects.PPM, (rect.getY() + rect.getHeight() / 2) / Insects.PPM);
                switchBody = world.createBody(switchBdef);
                shape.setAsBox((rect.getWidth() / 2) / Insects.PPM, (rect.getHeight() / 2) / Insects.PPM);
                switchfdef.shape = shape;
                switchfdef.isSensor = true;
                switchfdef.filter.categoryBits = Insects.SWITCH_BIT;
                switchfdef.filter.maskBits = Insects.INSECT_BIT | Insects.BASE_BIT;

                switchBody.createFixture(switchfdef).setUserData(Constants.MAP_SWITCH);
                levelSwitch = new LevelSwitch(switchBody, insects);
                switchBody.setUserData(levelSwitch);

            }



        //Switch
        BodyDef levelEndBlockBdef = new BodyDef();
        FixtureDef levelEndBlockfdef = new FixtureDef();
        Body levelEndClockBody;
        if(map.getLayers().get(Constants.MAP_LEVEL_END_BLOCK) != null)
            for (MapObject object : map.getLayers().get(Constants.MAP_LEVEL_END_BLOCK).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                levelEndBlockBdef.type = BodyDef.BodyType.StaticBody;
                levelEndBlockBdef.position.set((rect.getX() + rect.getWidth() / 2) / Insects.PPM, (rect.getY() + rect.getHeight() / 2) / Insects.PPM);
                levelEndClockBody = world.createBody(levelEndBlockBdef);
                shape.setAsBox((rect.getWidth() / 2) / Insects.PPM, (rect.getHeight() / 2) / Insects.PPM);
                levelEndBlockfdef.shape = shape;
//                levelEndBlockfdef.isSensor = true;
                levelEndBlockfdef.filter.categoryBits = Insects.BRICK_BIT;
                levelEndBlockfdef.filter.maskBits = Insects.INSECT_BIT | Insects.BASE_BIT;

                levelEndClockBody.createFixture(levelEndBlockfdef).setUserData(Constants.MAP_LEVEL_END_BLOCK);
                levelEndBlock = new LevelEndBlock(levelEndClockBody, insects);
                levelEndClockBody.setUserData(levelEndBlock);

            }
    }
}
