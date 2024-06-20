package com.example.quidon.gamejava;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Lvl1_Controller {
    @FXML
    private ImageView character;

    @FXML
    private ImageView ball;
    @FXML
    private ImageView boss;
    @FXML
    private ImageView barrier;
    @FXML
    private Label scoreLabel;


    //              sound           //
    private final String soundFile = "/com/example/quidon/gamejava/sound/musicSc1.mp3";
    private final String boss_get_damage = "/com/example/quidon/gamejava/sound/damage.mp3";
    private final String barrier_teleport = "/com/example/quidon/gamejava/sound/teleport.mp3";


    //              int             //
    private int currentImageIndex = 0;
    private int bossImageId = 0;
    private static int previousNumber = 0;
    private int bossHP = 10;


    //           Image          //
    private Image[] characterImages;
    private Image[] bossImages;


    //          float           //
    private final float ball_speed = 12.0f;
    private float speed = 3.84f;
    private final float fall_speed = 4.8f;



    //          boolean       //
    private boolean isJump = false;
    private boolean right = false;
    private boolean left = false;
    private boolean attacking = false;
    private boolean boss_damaged = false;
    private boolean boss_jumping = false;
    private boolean Win = false;


    private final Image jumpImage = new Image(getClass().getResource("/com/example/quidon/gamejava/images/jump_state.png").toExternalForm());
    private final Image bossJumpImage = new Image(getClass().getResource("/com/example/quidon/gamejava/images/boss_jump.png").toExternalForm());
    private final Image bossImage = new Image(getClass().getResource("/com/example/quidon/gamejava/images/boss.png").toExternalForm());


    private static final Random random = new Random();
    private Timeline bossImageTimeline;



    //              Win               //

    AnimationTimer animationTimer2 = new AnimationTimer() {
        private long startTime;

        @Override
        public void start() {
            super.start();
            startTime = System.nanoTime();
        }

        @Override
        public void handle(long now) {
            if (Win) {
                long elapsedTime = now - startTime;
                if (elapsedTime >= 7_000_000_000L) {
                    goToMenu();
                    stop();
                }
            }
        }
    };




    //              Boss Animation             //
    AnimationTimer collisionTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (attacking && ball.getBoundsInParent().intersects(barrier.getBoundsInParent()) && !Win) {
                ball.setLayoutX(250);
                ball.setLayoutY(-50);
                attacking = false;
            }
        }
    };

    AnimationTimer boss_timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (boss_damaged && !Win) {
                bossImages = new Image[]{
                        new Image(getClass().getResource("/com/example/quidon/gamejava/images/boss_damaged.png").toExternalForm()),
                        new Image(getClass().getResource("/com/example/quidon/gamejava/images/boss.png").toExternalForm()),
                };

                boss.setImage(bossImages[bossImageId]);

                if (bossImageTimeline != null && bossImageTimeline.getStatus() != Animation.Status.RUNNING) {
                    bossImageTimeline.play();
                }
            }
        }
    };


    AnimationTimer bossInAir = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (!Win) {
            if (boss_jumping && boss.getLayoutY() > 10) {
                boss.setLayoutY(boss.getLayoutY() - speed);
            } else if (boss.getLayoutY() <= 156) {
                boss_jumping = false;
                boss.setLayoutY(boss.getLayoutY() + fall_speed);
                boss.setImage(bossImage);
            }

            if (boss_jumping && boss.getLayoutY() > 10 && !boss_damaged) {
                boss.setImage(bossJumpImage);
            } else if (boss.getLayoutY() <= 156 && !boss_damaged) {
                boss.setImage(bossImage);
            }

        }
            }
    };

    Thread jumpingThread = new Thread(() -> {
        while (true && !Win) {
            try {
                long delay = ThreadLocalRandom.current().nextLong(1800, 3000);
                Thread.sleep(delay);
                boss_jumping = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });


    //                 Animations           //
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (!Win) {
                // Персонаж
                if (isJump && character.getLayoutY() > 10) {
                    character.setLayoutY(character.getLayoutY() - speed);
                    character.setImage(jumpImage);
                } else if (character.getLayoutY() <= 208) {
                    isJump = false;
                    character.setLayoutY(character.getLayoutY() + fall_speed);
                }

                if (right && character.getLayoutX() < 220) {
                    character.setLayoutX(character.getLayoutX() + speed);
                }
                if (left && character.getLayoutX() > -17) {
                    character.setLayoutX(character.getLayoutX() - speed);
                }

                if (attacking) {
                    ball.setLayoutX(ball.getLayoutX() + ball_speed);
                    if (ball.getLayoutX() > 620) {
                        ball.setLayoutX(250);
                        ball.setLayoutY(-50);
                        attacking = false;

                    } else if (ball.getBoundsInParent().intersects(boss.getBoundsInParent()) && !boss_damaged) {
                        boss_damaged = true;
                        ball.setLayoutX(250);
                        ball.setLayoutY(-50);

                        if (mediaPlayerBoss != null) {
                            mediaPlayerBoss.stop();
                            mediaPlayerBoss.dispose();
                        }

                        Media sound = new Media(getClass().getResource(boss_get_damage).toExternalForm());
                        mediaPlayerBoss = new MediaPlayer(sound);

                        playBossDamageSound();
                        bossHP = bossHP - 1;
                        updateScoreLabel();
                        attacking = false;

                        if (bossHP <= 0) {
                            Win = true;
                        }
                    }
                }
            } else {
                stopAllSounds();
            }

        }
    };



    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayerBoss;
    private MediaPlayer mediaPlayerTp;


    @FXML
    void initialize() {

            //                                 Music                                      //
            Media sound = new Media(getClass().getResource(soundFile).toExternalForm());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setVolume(0.5);

            playSound();


            //                                  Animation                                 //

            if (!isJump && character.getLayoutY() == 208) {
                characterImages = new Image[]{
                        new Image(getClass().getResource("/com/example/quidon/gamejava/images/down_state.png").toExternalForm()),
                        new Image(getClass().getResource("/com/example/quidon/gamejava/images/normal_state.png").toExternalForm())
                };

                character.setImage(characterImages[currentImageIndex]);

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(500), e -> changeImage())
                );
                timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
                timeline.play();

            }
            timer.start();


            //                  Boss                    //
            bossImageTimeline = new Timeline();
            for (int i = 0; i < 6; i++) {
                int finalI = i;
                bossImageTimeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(200 * (finalI + 1)), e -> changeBossImage())
                );
            }
            bossImageTimeline.setOnFinished(event -> {
                boss_damaged = false;
                boss.setImage(bossImages[1]);
            });
            boss_timer.start();


            //                  Boss defend                 //
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

            Runnable task = () -> {
                barrierPosition(generateRandomNumber());
            };

            executor.scheduleAtFixedRate(task, 0, 1500, TimeUnit.MILLISECONDS);


            //              Boss Blinking           //
            if (!boss_damaged && !boss_jumping) {

            }

            bossInAir.start();
            collisionTimer.start();
            jumpingThread.start();
            animationTimer2.start();


            //                        Keys                          //
            character.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (!Win) {
                        if (keyEvent.getCode() == KeyCode.D) {
                            right = true;
                        } else if (keyEvent.getCode() == KeyCode.A) {
                            left = true;
                        }

                        if (keyEvent.getCode() == KeyCode.SPACE && !isJump && character.getLayoutY() >= 208) {
                            isJump = true;
                        }

                        if (keyEvent.getCode() == KeyCode.F && !attacking) {
                            attacking = true;
                            ball.setLayoutY(character.getLayoutY() + 10);
                            ball.setLayoutX(character.getLayoutX() + 30);
                        }
                    }
                }
            });

            character.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (!Win) {
                        if (keyEvent.getCode() == KeyCode.D) {
                            right = false;
                        } else if (keyEvent.getCode() == KeyCode.A) {
                            left = false;
                        }
                    }
                }
            });
            character.setFocusTraversable(true);
    }


    //                         Functions                       //
    private void playSound() {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        mediaPlayer.play();
    }

    private void playBossDamageSound() {
        if (mediaPlayerBoss.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayerBoss.stop();
        }
        mediaPlayerBoss.play();
    }

    private void playTp() {
        if (!Win) {
            if (mediaPlayerTp.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayerTp.stop();
            }
            mediaPlayerTp.play();
        }
    }

    private void changeImage() {
        if (!Win) {
            currentImageIndex = (currentImageIndex + 1) % characterImages.length;
            character.setImage(characterImages[currentImageIndex]);
        }
    }

    private void changeBossImage() {
        bossImageId = (bossImageId + 1) % bossImages.length;
        boss.setImage(bossImages[bossImageId]);
    }


    private static int generateRandomNumber() {
        int randomNumber;
        do {
            randomNumber = ThreadLocalRandom.current().nextInt(1, 4);
        } while (randomNumber == previousNumber);
        previousNumber = randomNumber;
        return randomNumber;
    }

    private void barrierPosition(int num) {
        if (!Win) {
        Media soundtp = new Media(getClass().getResource(barrier_teleport).toExternalForm());
        mediaPlayerTp = new MediaPlayer(soundtp);
        mediaPlayerTp.setVolume(0.1);

        switch (num) {
            case 1:
                Platform.runLater(() -> {
                barrier.setLayoutY(227);
                playTp();
                });
                break;
            case 2:
                Platform.runLater(() -> {
                    barrier.setLayoutY(130);
                    playTp();
                });
                break;
            case 3:
                Platform.runLater(() -> {
                    barrier.setLayoutY(25);
                    playTp();
                });
                break;
        }
        }
    }

    private void stopAllSounds() {
        if (Win) {
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }
            if (mediaPlayerBoss != null && mediaPlayerBoss.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayerBoss.stop();
            }
            if (mediaPlayerTp != null && mediaPlayerTp.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayerTp.stop();
            }
        }
    }
    public void updateScoreLabel() {
        scoreLabel.setText(String.valueOf(bossHP));
    }

    private void goToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) character.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
