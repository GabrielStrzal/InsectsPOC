package com.gstrzal.insects.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gstrzal.insects.Insects;

/**
 * Created by Gabriel on 15/10/2017.
 */

public class LBug extends Sprite {


    public World world;
    public Body b2body;

    //joaninha
    public static final int WIDTH = 32;
    public static final int HEIGHT = 52;

    private float animationTimer = 0;
    private final Animation walking;
    private final TextureRegion standing;
    private final TextureRegion jumpUp;
    private final TextureRegion jumpDown;

    public float xSpeed = 0;
    public float ySpeed = 0;



    public LBug(World world, Texture texture){
        super(texture);
        this.world = world;

        TextureRegion[] regions = TextureRegion.split(texture, WIDTH, HEIGHT)[0];
        walking = new Animation(0.25F, regions[0], regions[1]);
        walking.setPlayMode(Animation.PlayMode.LOOP);
        standing = regions[0];
        jumpUp = regions[2];
        jumpDown = regions[3];

        defineLBug();
        setBounds(0,0, WIDTH/Insects.PPM, HEIGHT/Insects.PPM);
    }

    public void update(float dt){

        animationTimer += dt;

        float xSpeed = b2body.getLinearVelocity().x;
        float ySpeed = b2body.getLinearVelocity().y;


        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight()/2);

        //set Texture
        TextureRegion regionToDraw = standing;
        if (xSpeed != 0) {
            regionToDraw = (TextureRegion) walking.getKeyFrame(animationTimer);
        }
        if (ySpeed > 0) {
            regionToDraw = jumpUp;
        } else if (ySpeed < 0) {
            regionToDraw = jumpDown;
        }
        if (xSpeed < 0) {
            if (!regionToDraw.isFlipX()) regionToDraw.flip(true,false);
        } else if (xSpeed > 0) {
            if (regionToDraw.isFlipX()) regionToDraw.flip(true,false);
        }
        setRegion(regionToDraw);
    }

    public void defineLBug(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ Insects.PPM, 52/ Insects.PPM ); //initial position
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body  = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16/ Insects.PPM ,26/ Insects.PPM );

        fdef.filter.categoryBits = Insects.ANT_BIT;
        fdef.filter.maskBits = Insects.DEFAULT_BIT | Insects.COIN_BIT | Insects.BRICK_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/Insects.PPM, 6 /Insects.PPM), new Vector2(2/Insects.PPM, 6 /Insects.PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("head");
    }
}