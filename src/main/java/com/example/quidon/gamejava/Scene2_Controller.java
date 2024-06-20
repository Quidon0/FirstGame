package com.example.quidon.gamejava;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

public class Scene2_Controller {
    @FXML
    private ImageView character;

    @FXML
    private ImageView ball;
    @FXML
    private ImageView airball;
    @FXML
    private Label score;


    private final float ball_speed = 10.8f;
    private float speed = 4.8f;
    private final float fall_speed = 7.2f;



    private boolean isJump = false;
    private boolean right = false;
    private boolean left = false;
    private boolean attacking = false;
    private boolean win = false;


    private MediaPlayer mediaPlayer;
    private MediaPlayer SFX;
    private final String soundFile = "/com/example/quidon/gamejava/sound/overworld.mp3";
    private final String sfxFile = "/com/example/quidon/gamejava/sound/bounceSFX.mp3";


    private final Image jumpImage = new Image(getClass().getResource("/com/example/quidon/gamejava/images/jump_state.png").toExternalForm());
    private Image[] characterImages;


    private int currentScore = 0;
    private int index = 0;
    private int currentImageIndex = 0;


    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (!win) {
                if (isJump && character.getLayoutY() > 10) {
                    character.setLayoutY(character.getLayoutY() - speed);
                    character.setImage(jumpImage);
                } else if (character.getLayoutY() <= 285) {
                    isJump = false;
                    character.setLayoutY(character.getLayoutY() + fall_speed);
                }

                if (right && character.getLayoutX() < 510) {
                    character.setLayoutX(character.getLayoutX() + speed);
                }
                if (left && character.getLayoutX() > -22) {
                    character.setLayoutX(character.getLayoutX() - speed);
                }

                if (currentScore >= 10) {
                    win = true;
                    mediaPlayer.stop();
                    SFX.stop();

                    goToLevel3();

                }
            }

        }
    };


    AnimationTimer balltime = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (attacking && !win) {
                ball.setLayoutX(ball.getLayoutX() + ball_speed);

                if (ball.getLayoutX() > 600) {
                    ball.setLayoutX(250);
                    ball.setLayoutY(-50);
                    attacking = false;

                } else if (ball.getBoundsInParent().intersects(airball.getBoundsInParent())) {
                    ball.setLayoutX(250);
                    ball.setLayoutY(-50);
                    attacking = false;
                    currentScore = currentScore + 1;
                    updateScoreLabel();
                    airball.setLayoutY(-150);

                    Media sfx = new Media(getClass().getResource(sfxFile).toExternalForm());
                    SFX = new MediaPlayer(sfx);
                    SFX.setVolume(0.9);

                    playSFX();
                }
            }
        }
    };

    @FXML
    void initialize() {
        Media sound = new Media(getClass().getResource(soundFile).toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.5);
        playSound();


        timer.start();
        balltime.start();
        startRepeatingThread();


        if (!isJump && character.getLayoutY() >= 280) {
            characterImages = new Image[]{
                    new Image(getClass().getResource("/com/example/quidon/gamejava/images/down_state.png").toExternalForm()),
                    new Image(getClass().getResource("/com/example/quidon/gamejava/images/normal_state.png").toExternalForm())
            };

            character.setImage(characterImages[currentImageIndex]);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(500), e -> changeImage())
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

        }


        character.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!win) {
                    if (keyEvent.getCode() == KeyCode.D) {
                        right = true;
                    } else if (keyEvent.getCode() == KeyCode.A) {
                        left = true;
                    }

                    if (keyEvent.getCode() == KeyCode.SPACE && !isJump && character.getLayoutY() >= 278) {
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
                if (!win) {
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

    private void changeImage() {
        if (!win) {
            currentImageIndex = (currentImageIndex + 1) % characterImages.length;
            character.setImage(characterImages[currentImageIndex]);
        }
    }

    private void playSound() {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        mediaPlayer.play();
    }

    private void playSFX() {
        if (SFX.getStatus() == MediaPlayer.Status.PLAYING) {
            SFX.stop();
        }
        SFX.play();
    }

    private void teleport(int num) {
        switch (num) {
            case 1:
                Platform.runLater(() -> {
                    airball.setLayoutX(399);
                    airball.setLayoutY(100);
                });
                break;
            case 2:
                Platform.runLater(() -> {
                    airball.setLayoutX(101);
                    airball.setLayoutY(244);
                });
                break;
            case 3:
                Platform.runLater(() -> {
                    airball.setLayoutX(539);
                    airball.setLayoutY(302);
                });
                break;
            case 4:
                Platform.runLater(() -> {
                    airball.setLayoutX(101);
                    airball.setLayoutY(47);
                });
                break;
            case 5:
                Platform.runLater(() -> {
                    airball.setLayoutX(292);
                    airball.setLayoutY(236);
                });
                break;
            case 6:
                Platform.runLater(() -> {
                    airball.setLayoutX(382);
                    airball.setLayoutY(26);
                });
                break;
            case 7:
                Platform.runLater(() -> {
                    airball.setLayoutX(272);
                    airball.setLayoutY(226);
                });
                break;
            case 8:
                Platform.runLater(() -> {
                    airball.setLayoutX(498);
                    airball.setLayoutY(67);
                });
                break;
            case 9:
                Platform.runLater(() -> {
                    airball.setLayoutX(82);
                    airball.setLayoutY(130);
                });
                break;
            case 10:
                Platform.runLater(() -> {
                    airball.setLayoutX(555);
                    airball.setLayoutY(170);
                });
                break;
        }
    }

    private void startRepeatingThread() {
        Thread repeatingThread = new Thread(() -> {
            while (!win) {
                try {
                    Thread.sleep(2000);
                    if (index > 9) {
                        index = 0;
                    }
                    index++;
                    teleport(index);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        repeatingThread.setDaemon(true);
        repeatingThread.start();
    }

    public void updateScoreLabel() {
        score.setText(String.valueOf(currentScore));
    }

    private void goToLevel3() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);

            Stage stage = (Stage) character.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
