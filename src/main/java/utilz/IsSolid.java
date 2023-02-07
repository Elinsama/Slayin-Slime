package utilz;

import org.mapeditor.core.*;

import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.stream.Collectors;

import static main.Game.*;

public class IsSolid {

    public static boolean canMoveHere(float x, float y, float width, float height, ObjectGroup levelBounds) {

        Rectangle2D.Float playerBox = new Rectangle2D.Float(x, y, width, height);
        List<Rectangle2D.Double> mapObjects = levelBounds.getObjects().stream().map(MapObject::getBounds)
                .map(r -> new Rectangle2D.Double(r.getX() * SCALE, r.getY() * SCALE, r.getWidth() * SCALE, r.getHeight() * SCALE))
                .filter(obj -> obj.intersects(playerBox)).collect(Collectors.toList());
        return mapObjects.isEmpty();

    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, ObjectGroup levelBounds) {
        // Check the pixel below bottomleft and bottomright
        if (!canMoveHere(hitbox.x, hitbox.y + hitbox.height + 1, hitbox.x + 1, hitbox.y + hitbox.height + 2, levelBounds))
            return canMoveHere(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, hitbox.x + hitbox.width + 1, hitbox.y + hitbox.height + 2, levelBounds);

        return true;
    }


}
