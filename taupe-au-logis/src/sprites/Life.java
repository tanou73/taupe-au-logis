package sprites;

import utils.PosUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Life extends Sprite {
	
	public Life (){
		super();
		
		super.setRegion(new Texture(Gdx.files.internal("img/life.png")));
		
		super.setSize(PosUtil.xUnite(30), PosUtil.yUnite(30));
	}
	
}
