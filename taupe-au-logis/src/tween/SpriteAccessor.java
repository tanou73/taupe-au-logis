package tween;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAccessor implements TweenAccessor<Sprite>{

	public static final int ALPHA = 0;
	public static final int POSY = 1;
	public static final int DEGREE = 2;
	public static final int POSX = 3;
	
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case POSY:
			returnValues[0] = target.getY();
			return 1;
		case POSX:
			returnValues[0] = target.getX();
			return 1;
		case DEGREE:
			returnValues[0] = target.getRotation();
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		case POSY:
			target.setY(newValues[0]);
			break;
		case POSX:
			target.setX(newValues[0]);
			break;
		case DEGREE:
			target.setRotation(newValues[0]);
			break;
		default:
			assert false;
			break;
		}
	}

	
}
