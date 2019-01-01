package abstractfactory;

import abstractfactory.items.EmptyShape;
import abstractfactory.items.RectangleShape;
import abstractfactory.items.TriangleShape;

/**
 * Pattern Abstract Factory
 * @autor aoliferov
 * @since 01.01.2019
 */
public interface ShapeAbstFactory {

    TriangleShape createTriangle();

    RectangleShape createRectangle();

    EmptyShape createEmpty();

}
