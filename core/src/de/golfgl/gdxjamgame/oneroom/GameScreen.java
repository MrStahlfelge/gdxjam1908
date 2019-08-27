package de.golfgl.gdxjamgame.oneroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.golfgl.gdxjamgame.oneroom.model.GameLogic;
import de.golfgl.gdxjamgame.oneroom.scene2d.AnimatedTable;
import de.golfgl.gdxjamgame.oneroom.scene2d.ItemQuizGroup;

public class GameScreen extends AbstractScreen {

    private final Image imFull;
    private final AnimatedTable mainTable;
    private final Image imEmpty;

    public GameScreen(GdxJamGame app) {
        super(app);

        Stack stack = new Stack();

        stack.setSize(stage.getWidth(), stage.getHeight());

        mainTable = new AnimatedTable();
        mainTable.pad(50);
        mainTable.setFillParent(true);
        imEmpty = new Image(new TextureRegionDrawable(app.backgroundEmpty));
        imFull = new Image(new TextureRegionDrawable(app.backgroundFull));

        mainTable.setTouchable(Touchable.enabled);
        stack.add(imEmpty);
        stack.add(imFull);
        stack.add(mainTable);

        fillTableWithTitle();

        stage.addActor(stack);


    }

    private void fillTableWithTitle() {
        Image gameTitle = new Image(new TextureRegionDrawable(app.title));

        gameTitle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTable.clearAnimated(.3f, new Runnable() {
                    @Override
                    public void run() {
                        fillTableWithIntroPage1();
                    }
                });
            }
        });

        mainTable.addAnimated(gameTitle, 0).expandY();
        mainTable.row();
        mainTable.addAnimated(new Label("Submission by MrStahlfelge. Tap the logo to continue.", app.labelStyle), .5f);
        mainTable.row().padTop(20);
        Label communityLabel = new Label("Or tap here to join the best libGDX community.", app.labelStyle);
        communityLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://discord.gg/9X4MMfH");
            }
        });
        mainTable.addAnimated(communityLabel, 2f);
    }

    private void fillTableWithIntroPage1() {
        mainTable.setBackground(new TextureRegionDrawable(app.bgWhite));
        mainTable.getColor().a = 0;
        mainTable.addAction(Actions.fadeIn(.5f, Interpolation.fade));

        mainTable.addMultilineLabelAnimated(new Label("On a warm day in August 2019, the core libGDX discord crew meet up " +
                "for the traditional summer party hosted by BambooBandit.", app.labelStyle), 1f)
                .expandY().width(.5f * GdxJamGame.nativeGameWidth);

        mainTable.row();
        mainTable.addMultilineLabelAnimated(new Label("Conversations and laughters flowed up, and if you listened closely, " +
                "there were stories told about glorious days.", app.labelStyle), 5f).expandY();

        mainTable.row();
        mainTable.addMultilineLabelAnimated(new Label("Glorious days.", app.labelStyle), 5f).expandY();

        mainTable.row();
        mainTable.addMultilineLabelAnimated(new Label("You know what I mean.",
                app.labelStyle), 2f).expandY();

        mainTable.row();
        mainTable.addMultilineLabelAnimated(new Label("It was a fabulous party, probably going into the annals of history.",
                app.labelStyle), 3f).expandY();

        mainTable.row();
        mainTable.addMultilineLabelAnimated(new Label("But all things come to an end.",
                app.labelStyle), 3f).expandY();

        mainTable.row();
        AnimatedTable smallTable = new AnimatedTable();
        // TODO make skiplabel only available after went through intro before
        Label skipLabel = new Label("Skip intro", app.labelStyle);
        skipLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTable.clearAnimated(.3f, new Runnable() {
                    @Override
                    public void run() {
                        imFull.addAction(Actions.fadeOut(1f, Interpolation.fade));
                        showNextItem();
                    }
                });
            }
        });
        smallTable.add(skipLabel).expandX().left();
        final Label continueLabel = new Label("Tap to continue.", app.labelStyle);
        smallTable.addAnimated(continueLabel, mainTable.getDelayTime() + 1f).right();
        mainTable.add(smallTable).expandY().fillX().bottom();

        mainTable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (continueLabel.getColor().a == 1) {
                    mainTable.fadeOutAllChildrenAndClear(1f, new Runnable() {
                        @Override
                        public void run() {
                            fillTableWithIntroPage2();
                        }
                    });
                }
            }
        });
    }

    private void fillTableWithIntroPage2() {
        imFull.addAction(Actions.fadeOut(1f, Interpolation.fade));

        mainTable.addMultilineLabelAnimated(new Label("After all friends were gone, BambooBandit " +
                "began to clean the room.", app.labelStyle), 2f)
                .expandY().width(.5f * GdxJamGame.nativeGameWidth);

        mainTable.row();
        mainTable.addMultilineLabelAnimated(new Label("And discovered multiple items that were forgotten by their owners.",
                app.labelStyle), 4f).expandY();


        mainTable.row();
        mainTable.addMultilineLabelAnimated(new Label("Can you help him finding out who owns what?",
                app.labelStyle), 4f).expandY();


        mainTable.row();
        final Label continueLabel = new Label("Can you? Tap to begin.", app.labelStyle);
        mainTable.addMultilineLabelAnimated(continueLabel, 2f).expandY();

        mainTable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (continueLabel.getColor().a == 1) {
                    mainTable.fadeOutAllChildrenAndClear(1f, new Runnable() {
                        @Override
                        public void run() {
                            fillTableWithIntroPage3();
                        }
                    });
                }
            }
        });
    }

    private void fillTableWithIntroPage3() {
        imFull.addAction(Actions.fadeOut(1f, Interpolation.fade));

        mainTable.addMultilineLabelAnimated(new Label("\"Ha! You want to help me?\", spoke BambooBandit.", app.labelStyle), 1f)
                .expandY().width(.5f * GdxJamGame.nativeGameWidth);

        mainTable.row();
        mainTable.addMultilineLabelAnimated(new Label("\"I appreciate that, but I need no help. For every item I found, I knew within "
                + GameLogic.MAX_ANSWER_TIME + " SECONDS to whom it belongs.\nCan you beat that?\"", app.labelStyle), 2f).expandY();


        mainTable.row();
        final Label continueLabel = new Label("Tap to try!", app.labelStyle);
        mainTable.addMultilineLabelAnimated(continueLabel, 2f).expandY();

        mainTable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (continueLabel.getColor().a == 1) {
                    mainTable.clearAnimated(.3f, new Runnable() {
                        @Override
                        public void run() {
                            showNextItem();
                        }
                    });
                }
            }
        });
    }

    private void showNextItem() {
        mainTable.setVisible(false);

        if (app.gameLogic.isBonusRound()) {

        } else if (app.gameLogic.hasItem()) {
            ItemQuizGroup group = new ItemQuizGroup(app.gameLogic.getItem(), app) {
                @Override
                protected void onDone() {
                    showNextItem();
                }
            };
            stage.addActor(group);
        } else {
            showDoneScreen();
        }

        // TODO Am Ende auf jeden Fall sagen, dass mehr Zeit im Discord Server verbracht werden sollte
    }

    private void showDoneScreen() {
        app.bgMusic.stop();
        mainTable.setVisible(true);
        mainTable.getColor().a = 0;
        mainTable.addAction(Actions.fadeIn(.5f, Interpolation.fade));

        mainTable.addMultilineLabelAnimated(new Label("You've made it through!", app.labelStyle), 1f)
                .expandY().width(.5f * GdxJamGame.nativeGameWidth);

        mainTable.row();
        mainTable.addMultilineLabelAnimated(new Label("You assigned " + app.gameLogic.getCorrectAnswers() + " items correctly!",
                app.labelStyle), 3f).expandY();

        // TODO Items zeigen

        // TODO Bonus

        String comment;
        if (app.gameLogic.getScore() <= 3)
            comment = "You can do better! Hang out on the Discord server and retry!";
        else if (app.gameLogic.getScore() <= 6)
            comment = "Very intermediate. You need to spend more time on the Discord server.";
        else if (app.gameLogic.getScore() <= 9)
            comment = "Not bad. But make sure to follow the Discord server more focused.";
        else
            comment = "Perfect. Did you count how often you retried?";

        mainTable.row();
        final Label lastLineLabel = new Label(comment, app.labelStyle);
        mainTable.addMultilineLabelAnimated(lastLineLabel, 3f).expandY();

        mainTable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (lastLineLabel.getColor().a == 1) {
                    imFull.addAction(Actions.fadeIn(.3f, Interpolation.fade));
                    mainTable.clearAnimated(.3f, new Runnable() {
                        @Override
                        public void run() {
                            mainTable.setBackground((Drawable) null);
                            app.gameLogic.reset();
                            fillTableWithTitle();
                        }
                    });
                }
            }
        });

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
