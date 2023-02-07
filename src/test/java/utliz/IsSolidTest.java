package utliz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapeditor.core.Map;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.io.TMXMapReader;
import utilz.IsSolid;

import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsSolidTest {

    private ObjectGroup assets;

    @BeforeEach
    public void init() throws Exception {
        TMXMapReader reader = new TMXMapReader();
        Map map = reader.readMap(getClass().getResource("/test-map.tsx"));
        assets = (ObjectGroup) map.getLayer(map.getLayerCount() - 1);
    }

    @Test
    public void shouldCanMove() {
        //when
        boolean test = IsSolid.canMoveHere(10, 12, 5, 5, assets);

        //then
        assertTrue(test, "this does not collide with the level bounds");
    }

    @Test
    public void cantMoveOnLevelBounds() {
        //when
        boolean test = IsSolid.canMoveHere(25, 25, 20, 20, assets);

        //then
        assertFalse(test, "this does collide with the level bounds");
    }

    @Test
    public void inLevelBounds() {
        //when
        boolean test = IsSolid.canMoveHere(5, 15, 20, 26, assets);

        //then
        assertFalse(test, "in the level bounds should not be possible");
    }

    @Test
    public void shouldBeOnFloor(){
        //when
        boolean test = IsSolid.IsEntityOnFloor(new Rectangle2D.Float(5, 20, 5, 5), assets);

        assertTrue(test, "entity should be on the ground");
    }

    @Test
    public void shouldBeInAir(){
        //when
        boolean test = IsSolid.IsEntityOnFloor(new Rectangle2D.Float(15, 30, 5, 5), assets);

        assertFalse(test, "entity should be in the air");
    }
}
