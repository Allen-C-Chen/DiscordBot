package macro;

public enum Hooks {
/**
 * Contains {@link Hooks#MouseMovementHook} and {@link Hooks#MouseButtonHook} items
 */
MouseHook,
/**
 * Contains {@link Hooks#KeyPressHook}, {@link Hooks#KeyReleaseHook} and {@link Hooks#KeyReleaseHook} items
 */
KeyHook,
/**
 * Contains {@link Hooks#MouseMoveHook} and {@link Hooks#MouseDragHook} items
 */
MouseMovementHook,
/**
 * Contains {@link Hooks#MousePressHook}, {@link Hooks#MouseReleaseHook} and {@link Hooks#MouseClickHook} items
 */
MouseButtonHook,
/**
 * Records mouse moving
 */
MouseMoveHook,
/**
 * Records mouse movement when button is pressed
 */
MouseDragHook,
/**
 * Records mouse button being pressed
 */
MousePressHook,
/**
 * Records mouse button being released
 */
MouseReleaseHook,
/**
 * Records a mouse click
 */
MouseClickHook,
/**
 * Records a key press
 */
KeyPressHook,
/**
 * Records a key release
 */
KeyReleaseHook,
/**
 * Records a key being typed
 */
KeyTypedHook;
}
