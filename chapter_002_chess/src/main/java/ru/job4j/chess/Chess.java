package ru.job4j.chess;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ru.job4j.chess.exceptions.FigureNotFoundException;
import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.exceptions.OccupiedWayException;
import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;
import ru.job4j.chess.firuges.black.*;
import ru.job4j.chess.firuges.white.*;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @autor Андрей
 * @since 31.05.2018
 */
public class Chess extends Application {
    private static final String JOB4J = "Шахматы на www.job4j.ru";
    private final int size = 8;
    private final Logic logic = new Logic();

    private Rectangle buildRectangle(int x, int y, int size, boolean white) {

        Consumer<String> sos = System.out::println;
        sos.accept("");

        Rectangle rect = new Rectangle();
        rect.setX(x * size);
        rect.setY(y * size);
        rect.setHeight(size);
        rect.setWidth(size);
        if (white) {
            rect.setFill(Color.WHITE);
        } else {
            rect.setFill(Color.GRAY);
        }
        rect.setStroke(Color.BLACK);
        return rect;
    }

    private Rectangle buildFigure(int x, int y, int size, String image) {
        Rectangle rect = new Rectangle();
        rect.setX(x);
        rect.setY(y);
        rect.setHeight(size);
        rect.setWidth(size);
        Image img = new Image(this.getClass().getClassLoader().getResource(image).toString());
        rect.setFill(new ImagePattern(img));
        final Rectangle momento = new Rectangle(x, y);
        rect.setOnDragDetected(
                event -> {
                    momento.setX(event.getX());
                    momento.setY(event.getY());
                }
        );
        rect.setOnMouseDragged(
                event -> {
                    rect.setX(event.getX() - size / 2);
                    rect.setY(event.getY() - size / 2);
                }
        );
        rect.setOnMouseReleased(
                event -> {
                    try {
                        if (logic.move(this.findBy(momento.getX(), momento.getY()), this.findBy(event.getX(), event.getY()))) {
                            rect.setX(((int) event.getX() / 40) * 40 + 5);
                            rect.setY(((int) event.getY() / 40) * 40 + 5);
                        } else {
                            rect.setX(((int) momento.getX() / 40) * 40 + 5);
                            rect.setY(((int) momento.getY() / 40) * 40 + 5);
                        }
                    } catch (ImpossibleMoveException ime) {
                        System.out.println("Некорректное перемещение!");
                        rect.setX(((int) momento.getX() / 40) * 40 + 5);
                        rect.setY(((int) momento.getY() / 40) * 40 + 5);
                    } catch (OccupiedWayException owe) {
                        System.out.println("Путь занят!");
                        rect.setX(((int) momento.getX() / 40) * 40 + 5);
                        rect.setY(((int) momento.getY() / 40) * 40 + 5);
                    } catch (FigureNotFoundException fnfe) {
                        System.out.println("Фигура не найдена!");
                    }
                }
        );
        return rect;
    }

    private Group buildGrid() {
        Group panel = new Group();
        for (int y = 0; y != this.size; y++) {
            for (int x = 0; x != this.size; x++) {
                panel.getChildren().add(
                        this.buildRectangle(x, y, 40, (x + y) % 2 == 0)
                );
            }
        }
        return panel;
    }

    @Override
    public void start(Stage stage) {
        BorderPane border = new BorderPane();
        HBox control = new HBox();
        control.setPrefHeight(40);
        control.setSpacing(10.0);
        control.setAlignment(Pos.BASELINE_CENTER);
        Button start = new Button("Начать");
        start.setOnMouseClicked(
                event -> this.refresh(border)
        );
        control.getChildren().addAll(start);
        border.setBottom(control);
        border.setCenter(this.buildGrid());
        stage.setScene(new Scene(border, 400, 400));
        stage.setTitle(JOB4J);
        stage.setResizable(false);
        stage.show();
        this.refresh(border);
    }

    private void refresh(final BorderPane border) {
        Group grid = this.buildGrid();
        this.logic.clean();
        border.setCenter(grid);
        this.buildWhiteTeam(grid);
        this.buildBlackTeam(grid);
    }

    private void buildBlackTeam(Group grid) {
        this.add(new PawnBlack(Cell.A7), grid);
        this.add(new PawnBlack(Cell.B7), grid);
        this.add(new PawnBlack(Cell.C7), grid);
        this.add(new PawnBlack(Cell.D7), grid);
        this.add(new PawnBlack(Cell.E7), grid);
        this.add(new PawnBlack(Cell.F7), grid);
        this.add(new PawnBlack(Cell.G7), grid);
        this.add(new PawnBlack(Cell.H7), grid);
        this.add(new RookBlack(Cell.A8), grid);
        this.add(new KnightBlack(Cell.B8), grid);
        this.add(new BishopBlack(Cell.C8), grid);
        this.add(new QeenBlack(Cell.D8), grid);
        this.add(new KingBlack(Cell.E8), grid);
        this.add(new BishopBlack(Cell.F8), grid);
        this.add(new KnightBlack(Cell.G8), grid);
        this.add(new RookBlack(Cell.H8), grid);
    }

    private void buildWhiteTeam(Group grid) {
        this.add(new PawnWhite(Cell.A2), grid);
        this.add(new PawnWhite(Cell.B2), grid);
        this.add(new PawnWhite(Cell.C2), grid);
        this.add(new PawnWhite(Cell.D2), grid);
        this.add(new PawnWhite(Cell.E2), grid);
        this.add(new PawnWhite(Cell.F2), grid);
        this.add(new PawnWhite(Cell.G2), grid);
        this.add(new PawnWhite(Cell.H2), grid);
        this.add(new RookWhite(Cell.A1), grid);
        this.add(new KnightWhite(Cell.B1), grid);
        this.add(new BishopWhite(Cell.C1), grid);
        this.add(new QeenWhite(Cell.D1), grid);
        this.add(new KingWhite(Cell.E1), grid);
        this.add(new BishopWhite(Cell.F1), grid);
        this.add(new KnightWhite(Cell.G1), grid);
        this.add(new RookWhite(Cell.H1), grid);
    }

    private void add(Figure figure, Group grid) {
        this.logic.add(figure);
        Cell position = figure.position();
        grid.getChildren().add(
                this.buildFigure(
                        position.x * 40 + 5,
                        position.y * 40 + 5,
                        30,
                        figure.icon()
                )
        );
    }

    private Cell findBy(double graphX, double graphY) {
        int x = (int) graphX / 40;
        int y = (int) graphY / 40;
        return Arrays.stream(Cell.values())
                .filter(cell -> cell.x == x && cell.y == y)
                .findFirst().orElse(Cell.A1);
    }
}
