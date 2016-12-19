package gdx.collectingitems;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import static com.badlogic.gdx.math.MathUtils.random;
import java.util.Random;

public class CollectingItems extends ApplicationAdapter implements InputProcessor {

    SpriteBatch batch;
    ShapeRenderer SR;
    BitmapFont font;
    boolean isHit, isCollecting;
    float fX, fY, fX2, fY2, fRad;
    int nGrowth, nLuck, nInvHit = 0;
    int[] arnLoot = new int[10];
    String[] arsLoot = new String[10];

    @Override
    public void create() {
        batch = new SpriteBatch();
        SR = new ShapeRenderer();
        font = new BitmapFont();
        Random random = new Random();
        fRad = 250;
        fX = Gdx.graphics.getWidth() / 2 - 25;
        fY = Gdx.graphics.getHeight() / 2 - 25;
        fX2 = Gdx.graphics.getWidth() / 2 - (fRad / 2);
        fY2 = Gdx.graphics.getHeight() / 2 - (fRad / 2);
        nGrowth = 0;
        for (int i = 0; i < 10; i++) {
            arnLoot[i] = 0;
        }
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapes();
        Movement();
        isTouching();
        collecting();
        Inventory();
    }

    public void Movement() {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            fY -= 2;
            fY2 -= 2;
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                fX -= 2;
                fX2 -= 2;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                fX += 2;
                fX2 += 2;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            fY += 2;
            fY2 += 2;
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                fX -= 2;
                fX2 -= 2;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                fX += 2;
                fX2 += 2;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            fX -= 2;
            fX2 -= 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            fX += 2;
            fX2 += 2;
        }
    }

    public void items() {
        for (int i = 0; i < 10; i++) {
            arsLoot[i] = Integer.toString(arnLoot[i]);
        }
        batch.begin();
        font.draw(batch, "Clay x " + arnLoot[0], 10, 220);
        font.draw(batch, "Stone x " + arnLoot[1], 10, 200);
        font.draw(batch, "Iron x " + arnLoot[2], 10, 180);
        font.draw(batch, "Silver x " + arnLoot[3], 10, 160);
        font.draw(batch, "Gold x " + arnLoot[4], 10, 140);
        font.draw(batch, "Sapphire x " + arnLoot[5], 10, 120);
        font.draw(batch, "Ruby x " + arnLoot[6], 10, 100);
        font.draw(batch, "Pearl x " + arnLoot[7], 10, 80);
        font.draw(batch, "Diamond x " + arnLoot[8], 10, 60);
        font.draw(batch, "Opal x " + arnLoot[9], 10, 40);
        batch.end();
    }

    public void shapes() {
        SR.begin(ShapeType.Line);
        SR.setColor(Color.CYAN);
        SR.ellipse(fX2, fY2, fRad, fRad);
        SR.end();
        SR.begin(ShapeType.Filled);
        SR.setColor(1, 1, 1, 1);
        SR.rect(fX, fY, 50, 50);
        SR.rect(550, 100, 50, 250);
        SR.end();
    }

    public boolean isTouching() {
        if (fX2 + fRad >= 550) {
            isHit = true;
            //System.out.println("Hit");
        } else {
            isHit = false;
            //System.out.println("No Hit");
        }
        return false;
    }

    public void collecting() {
        if (isCollecting == true && isHit == true) {
            SR.begin(ShapeType.Filled);
            SR.setColor(Color.YELLOW);
            SR.rect(525, 50, nGrowth, 20);
            SR.end();
            nGrowth++;
            if (nGrowth >= 100) {
                //isCollecting = false;
                nLuck = random.nextInt(50) + 1;
                System.out.println(nLuck);
                System.out.println("Press Space to stop.");
                luck();
                nGrowth = 0;
            }
        }
    }

    public void luck() {
        if (nLuck >= 1) {
            arnLoot[0]++;
            if (nLuck > 15 && nLuck < 35) {
                arnLoot[1]++;
            } else if (nLuck < 20) {
                arnLoot[2]++;
            } else if (nLuck == 10) {
                arnLoot[3]++;
            } else if (nLuck == 20) {
                arnLoot[4]++;
            } else if (nLuck == 30) {
                arnLoot[5]++;
            } else if (nLuck == 40) {
                arnLoot[6]++;
            } else if (nLuck == 11 || nLuck == 19) {
                arnLoot[7]++;
            } else if (nLuck == 28 || nLuck == 39 || nLuck == 48) {
                arnLoot[8]++;
            } else if (nLuck == 49) {
                arnLoot[9]++;
            }
        }
    }

    public void Inventory() {
        if (nInvHit == 0) {
            SR.begin(ShapeType.Filled);
            SR.setColor(Color.GRAY);
            SR.rect(0, 0, 115, 30);
            SR.end();
            batch.begin();
            font.draw(batch, "Inventory", 25, 20);
            batch.end();
        } else if (nInvHit == 1) {
            SR.begin(ShapeType.Filled);
            SR.setColor(Color.GRAY);
            SR.rect(0, 0, 200, 250);
            SR.end();
            SR.begin(ShapeType.Filled);
            SR.setColor(Color.RED);
            SR.rect(180, 230, 20, 20);
            SR.end();
            batch.begin();
            font.draw(batch, "X", 185, 245);
            batch.end();
            items();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        if (c == ' ') {
            isCollecting = false;
            isHit = false;
            nGrowth = 0;
        }
        if (nInvHit == 1) {
            if (c == 'i' || c == 'I') {
                nInvHit = 0;
            }
        } else if (nInvHit == 0) {
            if (c == 'i' || c == 'I') {
                nInvHit = 1;
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        if (isHit == true) {
            if (Gdx.input.getX() >= 550
                    && Gdx.input.getX() <= 600
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) >= 100
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) <= 350) {
                isCollecting = true;
                System.out.println("YESS");
            }
        }
        if (nInvHit == 0) {
            if (Gdx.input.getX() >= 0
                    && Gdx.input.getX() <= 115
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) >= 0
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) <= 30) {
                nInvHit = 1;
            }
        } else if (nInvHit == 1) {
            if (Gdx.input.getX() >= 180
                    && Gdx.input.getX() <= 200
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) >= 230
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) <= 250) {
                nInvHit = 0;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
