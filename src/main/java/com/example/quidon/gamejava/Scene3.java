package com.example.quidon.gamejava;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Scene3 {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView character_lvl3;
    @FXML
    private ImageView confeti;

    @FXML
    private ImageView lazer_red1;

    @FXML
    private ImageView lazer_red2;

    @FXML
    private ImageView lazer_green;

    @FXML
    private ImageView screen_image;

    @FXML
    private AnchorPane screen;

    @FXML
    private Text you_dead;


    private final float lazer_speed = 3.0f;
    private final float speed = 0.2f;
    private final float speed_LR = 0.5f;
    private final float speed_down = 0.15f;
    private final float enemy_speed = 0.4f;
    private final float enemy_speed_die = 0.4f;



    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean attacking = false;
    private boolean win = false;
    private boolean delay = false;
    private boolean lose = false;
    private boolean btnOn = false;


    private MediaPlayer mediaPlayer;
    private final String soundFile = "/com/example/quidon/gamejava/sound/level3.mp3";

    private MediaPlayer lazerSFX;
    private final String lazerSoundPath = "/com/example/quidon/gamejava/sound/lazerSFX.wav";

    private MediaPlayer dieSFX;
    private final String dieSoundPath = "/com/example/quidon/gamejava/sound/damage.mp3";

    private MediaPlayer winSFX;
    private final String winPath = "/com/example/quidon/gamejava/sound/winSoundEffect.mp3";

    private double sceneWidth = 600.0;
    private double sceneHeight = 400.0;
    private double characterWidth = 18.0;
    private double characterHeight = 26.0;
    private double zoomFactor = 2.4;


    private double startPosX = 0;
    private double startPosY = 0;
    private double screenStartPosX = 0;
    private double screenStartPosY = 0;
    private double rootPosX = 0;
    private double rootPosY = 0;

    private List<EnemyPos> initialEnemyPositions;


    // Enemies
    @FXML
    private ImageView bg1;

    @FXML
    private ImageView bg2;

    @FXML
    private ImageView bg3;

    @FXML
    private ImageView bg4;

    @FXML
    private ImageView bg5;

    @FXML
    private ImageView bg6;



    @FXML
    private ImageView e1;

    @FXML
    private ImageView e10;

    @FXML
    private ImageView e11;

    @FXML
    private ImageView e12;

    @FXML
    private ImageView e13;

    @FXML
    private ImageView e14;

    @FXML
    private ImageView e15;

    @FXML
    private ImageView e16;

    @FXML
    private ImageView e17;

    @FXML
    private ImageView e18;

    @FXML
    private ImageView e19;

    @FXML
    private ImageView e2;

    @FXML
    private ImageView e20;

    @FXML
    private ImageView e21;

    @FXML
    private ImageView e3;

    @FXML
    private ImageView e4;

    @FXML
    private ImageView e5;

    @FXML
    private ImageView e6;

    @FXML
    private ImageView e7;

    @FXML
    private ImageView e8;

    @FXML
    private ImageView e9;

    @FXML
    private ImageView en1;

    @FXML
    private ImageView en10;

    @FXML
    private ImageView en11;

    @FXML
    private ImageView en12;

    @FXML
    private ImageView en13;

    @FXML
    private ImageView en14;

    @FXML
    private ImageView en15;

    @FXML
    private ImageView en16;

    @FXML
    private ImageView en17;

    @FXML
    private ImageView en18;

    @FXML
    private ImageView en19;

    @FXML
    private ImageView en2;

    @FXML
    private ImageView en20;

    @FXML
    private ImageView en3;

    @FXML
    private ImageView en4;

    @FXML
    private ImageView en5;

    @FXML
    private ImageView en6;

    @FXML
    private ImageView en7;

    @FXML
    private ImageView en8;

    @FXML
    private ImageView en9;

    @FXML
    private Button continue_game;

    @FXML
    private Button quit_game;

    private ObservableList<ImageView> enemiesGroup1;
    private ObservableList<ImageView> enemiesGroup2;
    private ObservableList<ImageView> peperoniGroup;

    private ObservableList<ImageView> bebeGroup;
    private ObservableList<ImageView> bombGroup;
    private ObservableList<ImageView> crabGroup;


    @FXML
    void btnClick(ActionEvent event) throws IOException {
        if (lose) {
            if (event.getSource() == continue_game && btnOn) {
                Restart();
            } else if (event.getSource() == quit_game && btnOn) {
                Platform.exit();
                System.exit(0);
            }
        } else if (win) {
            if (event.getSource() == continue_game && btnOn) {
                restartApplication();
            } else if (event.getSource() == quit_game && btnOn) {
                Platform.exit();
                System.exit(0);
            }
        }
    }




    //              All Animations          //
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {

                if (right && character_lvl3.getLayoutX() < 498) {
                    character_lvl3.setLayoutX(character_lvl3.getLayoutX() + speed_LR);

                    if (character_lvl3.getLayoutX() > 118 && character_lvl3.getLayoutX() < 381) {
                        screen.setLayoutX(screen.getLayoutX() + speed_LR);
                    }
                }

                if (left && character_lvl3.getLayoutX() > 3) {
                    character_lvl3.setLayoutX(character_lvl3.getLayoutX() - speed_LR);

                    if (character_lvl3.getLayoutX() > 118 && character_lvl3.getLayoutX() < 381) {
                        screen.setLayoutX(screen.getLayoutX() - speed_LR);
                    }

                }
                if (up && character_lvl3.getLayoutY() > 110) {
                    character_lvl3.setLayoutY(character_lvl3.getLayoutY() - speed);
                    screen.setLayoutY(screen.getLayoutY() - speed);
                }
                if (down && character_lvl3.getLayoutY() < 816) {
                    character_lvl3.setLayoutY(character_lvl3.getLayoutY() + speed_down);
                    screen.setLayoutY(screen.getLayoutY() + speed_down);
                }

                if (character_lvl3.getLayoutY() <= 120) {
                    Win();
                }

        }
    };


                //                  Camera                  //
    AnimationTimer camera_timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            double characterX = character_lvl3.getLayoutX() + characterWidth / 2.0;
            double characterY = character_lvl3.getLayoutY() + characterHeight / 2.0;

            double cameraX = characterX - (sceneWidth / 2.0);
            double cameraY = characterY - (sceneHeight / 2.0);


            if (character_lvl3.getLayoutX() > 118 && character_lvl3.getLayoutX() < 381) {
                rootPane.setLayoutX(-cameraX * zoomFactor);
            }

            rootPane.setLayoutY(-cameraY * zoomFactor);


            rootPane.setScaleX(zoomFactor);
            rootPane.setScaleY(zoomFactor);
        }
    };


    //                  Lasers                  //
    AnimationTimer lasers_timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (attacking) {
                lazer_green.setLayoutY(lazer_green.getLayoutY() - lazer_speed);
                lazer_red1.setLayoutY(lazer_green.getLayoutY() - lazer_speed);
                lazer_red2.setLayoutY(lazer_green.getLayoutY() - lazer_speed);

                if (lazer_green.getLayoutY() - character_lvl3.getLayoutY() < -100) {
                    lazer_green.setLayoutX(-50);
                    lazer_red1.setLayoutX(-70);
                    lazer_red2.setLayoutX(-90);


                    attacking = false;

                    Timeline delayTimeline = new Timeline(new KeyFrame(Duration.millis(800), evt -> {
                        delay = false;
                    }));
                    delayTimeline.play();
                }


            }
        }
    };




    //            Enemies Group 1         //
    AnimationTimer enemies1_timer = new AnimationTimer() {
        @Override
        public void handle(long l) {

            for (ImageView enemy : enemiesGroup1) {
                enemy.setLayoutX(enemy.getLayoutX() + enemy_speed);
                if (enemy.getLayoutX() >= 520) {
                    enemy.setLayoutX(-30);
                }


                if (enemy.getBoundsInParent().intersects(lazer_green.getBoundsInParent()) || enemy.getBoundsInParent().intersects(lazer_red1.getBoundsInParent())
                        || enemy.getBoundsInParent().intersects(lazer_red2.getBoundsInParent())) {
                    Image enemyImage = enemy.getImage();

                    if (enemyImage != null) {
                        if (bebeGroup.contains(enemy)) {
                            enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/enemy_bebe_damaged.png")));

                            AnimationTimer animationTimer = new AnimationTimer() {
                                @Override
                                public void handle(long l) {
                                    enemy.setLayoutX(enemy.getLayoutX() - enemy_speed_die);
                                    enemy.setLayoutY(enemy.getLayoutY() - enemy_speed_die);
                                }
                            };
                            animationTimer.start();

                            Timeline removeTimeline = new Timeline(new KeyFrame(Duration.seconds(2), evt -> {
                                enemy.setLayoutY(-100);
                                enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/enemy_bebe.png")));
                                animationTimer.stop();
                            }));
                            removeTimeline.play();


                        } else if (bombGroup.contains(enemy)) {
                            enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/gif/boom.gif")));

                            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(650), evt -> {
                                enemy.setLayoutY(-100);
                                enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/enemy_bomb.png")));
                            }));
                            timeline.play();



                        } else if (crabGroup.contains(enemy)) {
                            enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/enemy_flycrab_damaged.png")));

                            AnimationTimer animationTimer = new AnimationTimer() {
                                @Override
                                public void handle(long l) {
                                    enemy.setLayoutX(enemy.getLayoutX() + enemy_speed_die);
                                    enemy.setLayoutY(enemy.getLayoutY() - enemy_speed_die);
                                }
                            };
                            animationTimer.start();

                            Timeline removeTimeline = new Timeline(new KeyFrame(Duration.seconds(2), evt -> {
                                enemy.setLayoutY(-100);
                                enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/enemy_flycrab.png")));
                                animationTimer.stop();
                            }));
                            removeTimeline.play();

                        }

                    }
                } else if (enemy.getBoundsInParent().intersects(character_lvl3.getBoundsInParent())) {
                    Lose();
                }
            }
        }
    };


    //            Enemies Group 2         //
    AnimationTimer enemies2_timer = new AnimationTimer() {
        @Override
        public void handle(long l) {


            for (ImageView enemy : enemiesGroup2) {

                enemy.setLayoutX(enemy.getLayoutX() + enemy_speed);
                if (enemy.getLayoutX() >= 520) {
                    enemy.setLayoutX(-30);
                }


                if (enemy.getBoundsInParent().intersects(lazer_green.getBoundsInParent()) || enemy.getBoundsInParent().intersects(lazer_red1.getBoundsInParent())
                        || enemy.getBoundsInParent().intersects(lazer_red2.getBoundsInParent())) {
                    Image enemyImage = enemy.getImage();

                    if (enemyImage != null) {
                        if (bebeGroup.contains(enemy)) {
                            enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/enemy_bebe_damaged.png")));

                            AnimationTimer animationTimer = new AnimationTimer() {
                                @Override
                                public void handle(long l) {
                                    enemy.setLayoutX(enemy.getLayoutX() - enemy_speed_die);
                                    enemy.setLayoutY(enemy.getLayoutY() - enemy_speed_die);
                                }
                            };
                            animationTimer.start();

                            Timeline removeTimeline = new Timeline(new KeyFrame(Duration.seconds(2), evt -> {
                                enemy.setLayoutY(-100);
                                enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/enemy_bebe.png")));
                                animationTimer.stop();
                            }));
                            removeTimeline.play();


                    } else if (bombGroup.contains(enemy)) {
                            enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/gif/boom.gif")));

                            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(650), evt -> {
                                enemy.setLayoutY(-100);
                                enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/enemy_bomb.png")));
                            }));
                            timeline.play();


                        } else if (crabGroup.contains(enemy)) {
                            enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/enemy_flycrab_damaged.png")));

                            AnimationTimer animationTimer = new AnimationTimer() {
                                @Override
                                public void handle(long l) {
                                    enemy.setLayoutX(enemy.getLayoutX() + enemy_speed_die);
                                    enemy.setLayoutY(enemy.getLayoutY() - enemy_speed_die);
                                }
                            };
                            animationTimer.start();

                            Timeline removeTimeline = new Timeline(new KeyFrame(Duration.seconds(2), evt -> {
                                enemy.setLayoutY(-100);
                                enemy.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/enemy_flycrab.png")));
                                animationTimer.stop();
                            }));
                            removeTimeline.play();

                        }

                    }
                } else if (enemy.getBoundsInParent().intersects(character_lvl3.getBoundsInParent())) {
                    Lose();
                }

            }
        }
    };


    //          Peperoni Group          //
    AnimationTimer pepe_timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for (ImageView enemy : peperoniGroup) {
                enemy.setLayoutX(enemy.getLayoutX() + enemy_speed);
                if (enemy.getLayoutX() >= 560) {
                    enemy.setLayoutX(-30);
                }
                if (enemy.getBoundsInParent().intersects(lazer_green.getBoundsInParent())) {
                    LazerStop(lazer_green);
                } else if (enemy.getBoundsInParent().intersects(lazer_red1.getBoundsInParent())) {
                    LazerStop(lazer_red1);
                } else if (enemy.getBoundsInParent().intersects(lazer_red2.getBoundsInParent())) {
                    LazerStop(lazer_red2);
                }


                if (enemy.getBoundsInParent().intersects(character_lvl3.getBoundsInParent())) {
                    Lose();
                }

            }

        }
    };


    //              Win             //
    AnimationTimer Win_Timer = new AnimationTimer() {
        private long startTime;
        private long delay1 = 500_000_000L;
        private long delay2 = 2_000_000_000L;
        private long delay3 = 2_800_000_000L;
        private boolean action1Completed = false;
        private boolean action2Completed = false;
        private boolean action3Completed = false;

        @Override
        public void handle(long now) {
            long elapsedTime = now - startTime;

            if (!action1Completed && elapsedTime >= delay1) {
                if (screen_image.getOpacity() < 1.0) {
                    screen_image.setOpacity(screen_image.getOpacity() + 0.01);
                    you_dead.setOpacity(you_dead.getOpacity() + 0.01);
                    confeti.setOpacity(confeti.getOpacity() + 0.01);
                } else {
                    action1Completed = true;
                }
            }

            if (!action2Completed && elapsedTime >= delay2) {
                if (continue_game.getOpacity() < 1.0) {
                    continue_game.setOpacity(continue_game.getOpacity() + 0.01);
                } else {
                    action2Completed = true;
                }
            }

            if (!action3Completed && elapsedTime >= delay3) {
                if (quit_game.getOpacity() < 1.0) {
                    quit_game.setOpacity(quit_game.getOpacity() + 0.01);
                } else {
                    action3Completed = true;
                    btnOn = true;
                }
            }

            if (action1Completed && action2Completed && action3Completed) {
                stop();
            }
        }

        @Override
        public void start() {
            super.start();
            startTime = System.nanoTime();


            action1Completed = false;
            action2Completed = false;
            action3Completed = false;
        }
    };





    //                      Lose                        //
    AnimationTimer Lose_Timer = new AnimationTimer() {
        private long startTime;
        private long delay1 = 500_000_000L;
        private long delay2 = 2_000_000_000L;
        private long delay3 = 2_800_000_000L;
        private boolean action1Completed = false;
        private boolean action2Completed = false;
        private boolean action3Completed = false;

        @Override
        public void handle(long now) {
            long elapsedTime = now - startTime;


            if (!action1Completed && elapsedTime >= delay1) {
                if (screen_image.getOpacity() < 1.0) {
                    screen_image.setOpacity(screen_image.getOpacity() + 0.01);
                    you_dead.setOpacity(you_dead.getOpacity() + 0.01);
                } else {
                    action1Completed = true;
                }
            }


            if (!action2Completed && elapsedTime >= delay2) {
                if (continue_game.getOpacity() < 1.0) {
                    continue_game.setOpacity(continue_game.getOpacity() + 0.01);
                } else {
                    action2Completed = true;
                }
            }


            if (!action3Completed && elapsedTime >= delay3) {
                if (quit_game.getOpacity() < 1.0) {
                    quit_game.setOpacity(quit_game.getOpacity() + 0.01);
                } else {
                    action3Completed = true;
                    btnOn = true;
                }
            }

            if (action1Completed && action2Completed && action3Completed) {
                stop();
            }
        }

        @Override
        public void start() {
            super.start();
            startTime = System.nanoTime();


            action1Completed = false;
            action2Completed = false;
            action3Completed = false;

        }
    };






    @FXML
    void initialize() {
        startPosX = character_lvl3.getLayoutX();
        startPosY = character_lvl3.getLayoutY();
        screenStartPosX = screen.getLayoutX();
        screenStartPosY = screen.getLayoutY();


        //              Enemies             //
        enemiesGroup1 = FXCollections.observableArrayList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e18, e19, e20, e21);
        enemiesGroup2 = FXCollections.observableArrayList(en1, en2, en3, en4, en5, en6, en7, en8, en9, en10, en11, en12, en13, en14, en15, en16, en17, en18, en19, en20);
        peperoniGroup = FXCollections.observableArrayList(bg1, bg2, bg3, bg4, bg5, bg6);
        bebeGroup = FXCollections.observableArrayList(e3, e7, e11, e14, e15, e19, en5, en6, en7, en13, en15, en16, en19);
        bombGroup = FXCollections.observableArrayList(e2, e4, e8, e9, e13, e17, e18, e21, en1, en4, en9, en12, en17, en20);
        crabGroup = FXCollections.observableArrayList(e1, e6, e5, e10, e12, e16, e20, en2, en3, en8, en10, en11, en14, en18);


        initialEnemyPositions = new ArrayList<>();

        for (ImageView enemy : enemiesGroup1) {
            initialEnemyPositions.add(new EnemyPos(enemy.getLayoutX(), enemy.getLayoutY()));
        }
        for (ImageView enemy : enemiesGroup2) {
            initialEnemyPositions.add(new EnemyPos(enemy.getLayoutX(), enemy.getLayoutY()));
        }


        //              Media               //
        Media sound = new Media(getClass().getResource(soundFile).toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.5);
        playSound();


        Media lazersfx = new Media(getClass().getResource(lazerSoundPath).toExternalForm());
        lazerSFX = new MediaPlayer(lazersfx);
        lazerSFX.setVolume(0.3);

        Media dieSfx = new Media(getClass().getResource(dieSoundPath).toExternalForm());
        dieSFX = new MediaPlayer(dieSfx);
        dieSFX.setVolume(0.3);

        Media winSfx = new Media(getClass().getResource(winPath).toExternalForm());
        winSFX = new MediaPlayer(winSfx);
        winSFX.setVolume(0.3);


        //          timers activation        //
        timer.start();
        camera_timer.start();
        lasers_timer.start();
        enemies1_timer.start();
        enemies2_timer.start();
        pepe_timer.start();

        rootPosX = rootPane.getLayoutX();
        rootPosY = rootPane.getLayoutY();


        //              Buttons             //
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLUE);


        quit_game.setOnMouseEntered(e -> quit_game.setEffect(dropShadow));
        quit_game.setOnMouseExited(e -> quit_game.setEffect(null));
        continue_game.setOnMouseEntered(e -> continue_game.setEffect(dropShadow));
        continue_game.setOnMouseExited(e -> continue_game.setEffect(null));



        //              KEYS            //
        character_lvl3.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!win && !lose) {
                    if (keyEvent.getCode() == KeyCode.D) {
                        right = true;
                    } else if (keyEvent.getCode() == KeyCode.A) {
                        left = true;
                    }

                    if (keyEvent.getCode() == KeyCode.W) {
                        up = true;
                    } else if (keyEvent.getCode() == KeyCode.S) {
                        down = true;
                    }

                    if (keyEvent.getCode() == KeyCode.F && !attacking && !delay) {
                        attacking = true;
                        delay = true;

                        lazer_green.setLayoutY(character_lvl3.getLayoutY());
                        lazer_green.setLayoutX(character_lvl3.getLayoutX() + 6);


                        lazer_red1.setLayoutY(character_lvl3.getLayoutY());
                        lazer_red1.setLayoutX(character_lvl3.getLayoutX() - 1);

                        lazer_red2.setLayoutY(character_lvl3.getLayoutY());
                        lazer_red2.setLayoutX(character_lvl3.getLayoutX() + 12);

                        playLazerSFX();
                    }
                }
            }
        });

        character_lvl3.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!win && !lose) {
                    if (keyEvent.getCode() == KeyCode.D) {
                        right = false;
                    } else if (keyEvent.getCode() == KeyCode.A) {
                        left = false;
                    }

                    if (keyEvent.getCode() == KeyCode.W) {
                        up = false;
                    } else if (keyEvent.getCode() == KeyCode.S) {
                        down = false;
                    }
                }
            }
        });
        character_lvl3.setFocusTraversable(true);
    }



    //          Methods         //
    private void playSound() {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        mediaPlayer.play();
    }

    private void playLazerSFX() {
        if (lazerSFX.getStatus() == MediaPlayer.Status.PLAYING) {
            lazerSFX.stop();
        }
        lazerSFX.play();
    }

    private void playDieSFX() {
        if (dieSFX.getStatus() == MediaPlayer.Status.PLAYING) {
            dieSFX.stop();
        }
        dieSFX.play();
    }

    private void playWinSFX() {
        if (winSFX.getStatus() == MediaPlayer.Status.PLAYING) {
            winSFX.stop();
        }
        winSFX.play();
    }

    private void Lose() {
        character_lvl3.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/gif/boom.gif")));
        screen_image.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/die_screen.png")));
        lose = true;
        timer.stop();
        enemies1_timer.stop();
        enemies2_timer.stop();
        lasers_timer.stop();
        mediaPlayer.stop();
        pepe_timer.stop();
        playDieSFX();

        Lose_Timer.start();
    }


    private void Win() {
        win = true;
        screen_image.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/winScreen.png")));
        timer.stop();
        enemies1_timer.stop();
        enemies2_timer.stop();
        lasers_timer.stop();
        mediaPlayer.stop();
        pepe_timer.stop();


        you_dead.setStyle("-fx-font-size: 24px;");
        you_dead.setText("Игра пройдена!");
        you_dead.setLayoutX(you_dead.getLayoutX() - 5);
        continue_game.setText("Начать заново");
        continue_game.setLayoutX(continue_game.getLayoutX() - 5);
        quit_game.setLayoutX(quit_game.getLayoutX() + 5);

        Win_Timer.start();
        playWinSFX();
    }

    private void LazerStop(ImageView laser) {
        laser.setLayoutX(-50);
    }

    private void Restart() {
        character_lvl3.setImage(new Image(getClass().getResourceAsStream("/com/example/quidon/gamejava/images/character_lvl3.png")));

        // Stop all timers
        timer.stop();
        camera_timer.stop();
        lasers_timer.stop();
        enemies1_timer.stop();
        enemies2_timer.stop();
        pepe_timer.stop();
        Lose_Timer.stop();

        // Reset player position
        character_lvl3.setLayoutX(startPosX);
        character_lvl3.setLayoutY(startPosY);

        // camera
        rootPane.setLayoutX(rootPosX);
        rootPane.setLayoutY(rootPosY);
        screen.setLayoutX(screenStartPosX);
        screen.setLayoutY(screenStartPosY);

        // Reset enemy positions
        int i = 0;

        for (ImageView enemy : enemiesGroup1) {
            EnemyPos initialPosition = initialEnemyPositions.get(i++);
            enemy.setLayoutX(initialPosition.getInitialX());
            enemy.setLayoutY(initialPosition.getInitialY());
        }
        for (ImageView enemy : enemiesGroup2) {
            EnemyPos initialPosition = initialEnemyPositions.get(i++);
            enemy.setLayoutX(initialPosition.getInitialX());
            enemy.setLayoutY(initialPosition.getInitialY());
        }


        screen_image.setOpacity(0.0);
        you_dead.setOpacity(0.0);
        continue_game.setOpacity(0.0);
        quit_game.setOpacity(0.0);

        // Reset other game state variables
        right = false;
        left = false;
        up = false;
        down = false;
        attacking = false;
        win = false;
        delay = false;
        lose = false;
        lazer_green.setLayoutX(-100);
        lazer_red1.setLayoutX(-110);
        lazer_red2.setLayoutX(-120);


        // Restart timers
        timer.start();
        camera_timer.start();
        lasers_timer.start();
        enemies1_timer.start();
        enemies2_timer.start();
        pepe_timer.start();
        btnOn = false;
        mediaPlayer.play();

        character_lvl3.requestFocus();
    }

    private void restartApplication() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



}
