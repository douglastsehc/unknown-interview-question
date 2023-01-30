package model;

import exception.InvalidEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CanvasTest {
    private Canvas canvas;

    @BeforeEach
    public void setUp()  {
        canvas = new CanvasImpl(20, 4);
    }

    @Test
    public void Should_pass_the_test_when_create_empty_rendor_canvas()  {
        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|                    |\n" +
                        "|                    |\n" +
                        "|                    |\n" +
                        "|                    |\n" +
                        "----------------------");
    }

    @Test
    public void Should_pass_the_test_when_create_vertical_line()  {
        Line line = new Line(1, 2, 1, 3);
        canvas.addEntity(line);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|                    |\n" +
                        "|x                   |\n" +
                        "|x                   |\n" +
                        "|                    |\n" +
                        "----------------------");

    }

    @Test
    public void Should_pass_the_test_even_vertical_line_is_trimmed()  {
        Line line = new Line(1, 2, 1, 22);
        canvas.addEntity(line);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|                    |\n" +
                        "|x                   |\n" +
                        "|x                   |\n" +
                        "|x                   |\n" +
                        "----------------------");

    }
    @Test
    public void Should_pass_the_test_when_line_is_horizontal()  {
        Line line = new Line(2, 2, 4, 2);
        canvas.addEntity(line);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|                    |\n" +
                        "| xxx                |\n" +
                        "|                    |\n" +
                        "|                    |\n" +
                        "----------------------");
    }

    @Test
    public void Should_pass_the_test_when_horizontal_line_is_trimmed()  {
        Line line = new Line(1, 2, 30, 2);
        canvas.addEntity(line);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|                    |\n" +
                        "|xxxxxxxxxxxxxxxxxxxx|\n" +
                        "|                    |\n" +
                        "|                    |\n" +
                        "----------------------");
    }

    @Test()
    public void Should_throw_error_when_the_line_is_outside_of_canvas()  {
        Assertions.assertThrows(InvalidEntityException.class, () -> {
            Line line1 = new Line(100, 20, 100, 22);
            canvas.addEntity(line1);
        });

    }

    @Test()
    public void Should_throw_error_when_the_rectangle_is_out_of_the_canvas()  {
        Assertions.assertThrows(InvalidEntityException.class, () -> {
            Rectangle rectangle = new Rectangle(100, 20, 100, 22);
            canvas.addEntity(rectangle);
        });
    }

    @Test()
    public void Should_pass_the_test_when_rectangle_is_inside_the_canvas()  {
        Rectangle rectangle = new Rectangle(14, 1, 18, 3);
        canvas.addEntity(rectangle);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|             xxxxx  |\n" +
                        "|             x   x  |\n" +
                        "|             xxxxx  |\n" +
                        "|                    |\n" +
                        "----------------------");
    }

    @Test()
    public void Should_pass_the_test_even_partical_rectangle_is_exceed_the_canvas()  {
        Rectangle rectangle = new Rectangle(2, 1, 4, 30);
        canvas.addEntity(rectangle);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "| xxx                |\n" +
                        "| x x                |\n" +
                        "| x x                |\n" +
                        "| x x                |\n" +
                        "----------------------");
    }

    @Test()
    public void Should_pass_the_test_even_the_rectangle_is_exceed_the_canvas()  {
        Rectangle rectangle = new Rectangle(2, 1, 40, 3);
        canvas.addEntity(rectangle);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "| xxxxxxxxxxxxxxxxxxx|\n" +
                        "| x                  |\n" +
                        "| xxxxxxxxxxxxxxxxxxx|\n" +
                        "|                    |\n" +
                        "----------------------");
    }

    @Test()
    public void Should_pass_the_test_even_part_of_the_rectangle_is_exceed_the_canvas()  {
        Rectangle rectangle = new Rectangle(2, 1, 40, 30);
        canvas.addEntity(rectangle);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "| xxxxxxxxxxxxxxxxxxx|\n" +
                        "| x                  |\n" +
                        "| x                  |\n" +
                        "| x                  |\n" +
                        "----------------------");
    }

    @Test()
    public void Should_throw_error_when_rectangle_is_outside_the_canvas()  {
        Assertions.assertThrows(InvalidEntityException.class, () -> {
            Rectangle rectangle = new Rectangle(20, 100, 40, 102);
            canvas.addEntity(rectangle);
        });
    }

    @Test()
    public void Should_pass_the_test_with_area_fill()  {
        Area rectangle = new Area(2, 1, 'o');
        canvas.addEntity(rectangle);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|oooooooooooooooooooo|\n" +
                        "|oooooooooooooooooooo|\n" +
                        "|oooooooooooooooooooo|\n" +
                        "|oooooooooooooooooooo|\n" +
                        "----------------------");
    }

    @Test
    public void Should_pass_the_test_when_fill_area_as_a_vertical_line()  {
        Line line = new Line(1, 2, 1, 3);
        canvas.addEntity(line);
        Area area = new Area(1, 2, 'o');
        canvas.addEntity(area);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|                    |\n" +
                        "|o                   |\n" +
                        "|o                   |\n" +
                        "|                    |\n" +
                        "----------------------");

    }

    @Test
    public void Should_pass_the_test_when_rectangle_with_area()  {
        Rectangle rectangle = new Rectangle(14, 1, 18, 3);
        canvas.addEntity(rectangle);
        Area area = new Area(14, 1, 'o');
        canvas.addEntity(area);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|             ooooo  |\n" +
                        "|             o   o  |\n" +
                        "|             ooooo  |\n" +
                        "|                    |\n" +
                        "----------------------");

    }

    @Test
    public void Should_pass_the_test_when_area_fill_with_rectangle()  {
        Rectangle rectangle = new Rectangle(14, 1, 18, 4);
        canvas.addEntity(rectangle);
        Area area = new Area(15, 2, 'o');
        canvas.addEntity(area);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|             xxxxx  |\n" +
                        "|             xooox  |\n" +
                        "|             xooox  |\n" +
                        "|             xxxxx  |\n" +
                        "----------------------");

    }

    @Test
    public void Should_pass_the_test_when_area_filled_the_canvas_except_the_rectangle_()  {
        Rectangle rectangle = new Rectangle(14, 1, 18, 4);
        canvas.addEntity(rectangle);
        Area area = new Area(10, 1, 'o');
        canvas.addEntity(area);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|oooooooooooooxxxxx  |\n" +
                        "|ooooooooooooox   x  |\n" +
                        "|ooooooooooooox   x  |\n" +
                        "|oooooooooooooxxxxx  |\n" +
                        "----------------------");
    }

    /*L 1 2 1 22
    L 6 3 166 3
    R 14 1 18 3
    B 1 3 o*/
    @Test
    public void Should_pass_the_test_when_multiple_line_in_canvas()  {
        Line line1 = new Line(1, 2, 6, 2);
        canvas.addEntity(line1);

        Line line2 = new Line(6, 3, 6, 4);
        canvas.addEntity(line2);

        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|                    |\n" +
                        "|xxxxxx              |\n" +
                        "|     x              |\n" +
                        "|     x              |\n" +
                        "----------------------");

        canvas.addEntity(new Rectangle(14, 1, 18, 3));
        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|             xxxxx  |\n" +
                        "|xxxxxx       x   x  |\n" +
                        "|     x       xxxxx  |\n" +
                        "|     x              |\n" +
                        "----------------------");

        canvas.addEntity(new Area(10, 3, 'o'));
        Assertions.assertEquals(canvas.render(),
                "----------------------\n" +
                        "|oooooooooooooxxxxxoo|\n" +
                        "|xxxxxxooooooox   xoo|\n" +
                        "|     xoooooooxxxxxoo|\n" +
                        "|     xoooooooooooooo|\n" +
                        "----------------------");
    }
}