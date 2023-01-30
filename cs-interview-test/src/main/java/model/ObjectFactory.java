package model;

import command.BucketFillCommand;
import command.CreateEntityCommand;
import command.CreateLineCommand;
import command.CreateRectangleCommand;

public class ObjectFactory {

    public Object getEntity(CreateEntityCommand command) {
        if (command instanceof CreateLineCommand) {
            CreateLineCommand createLineCommand = (CreateLineCommand) command;
            return new Line(createLineCommand.getX1(), createLineCommand.getY1(), createLineCommand.getX2(), createLineCommand.getY2());
        }   else if (command instanceof CreateRectangleCommand) {
            CreateRectangleCommand createRectangleCommand = (CreateRectangleCommand) command;
            return new Rectangle(createRectangleCommand.getX1(), createRectangleCommand.getY1(), createRectangleCommand.getX2(), createRectangleCommand.getY2());
        }   else if (command instanceof BucketFillCommand) {
            BucketFillCommand bucketFillCommand = (BucketFillCommand) command;
            return new Area(bucketFillCommand.getX(), bucketFillCommand.getY(), bucketFillCommand.getCharacter());
        }
        return null;
    }
}
