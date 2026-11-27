# JavaFX

JavaFX is Java's modern GUI toolkit for building hardware-accelerated desktop applications. When paired with a Java EE backend, it enables **end-to-end Java development**, allowing teams to **share domain models**, **validation rules** and **type-safe contracts** between client and server without maintaining a separate web frontend stack.

# Core Concepts

## Stage, Scene and Scene Graph Hierarchy

JavaFX structures every desktop interface using a strict theatrical metaphor backed by a retained-mode ***graphics engine*** (**Prism**) and a ***native windowing layer*** (**Glass**).

- **JavaFX Application** (`Application`): The entry point of a desktop client. The runtime invokes `start(Stage primaryStage)` on the JavaFX Application Thread to initialize the user interface.

- **Stage** (`javafx.stage.Stage`): The top-level native OS window (complete with title bar, borders and minimize/close controls). Your application receives a `primaryStage` at launch and can open secondary `Stage` instances for dialogs or auxiliary windows.

- **Scene** (`javafx.scene.Scene`): The drawable content surface housed inside a `Stage`. A `Stage` displays only one `Scene` at a time, though you can swap different `Scene` instances onto the same `Stage` to navigate between views.

- **Scene Graph** (`javafx.scene.Node`): A hierarchical tree of visual elements rendered inside a `Scene`. Every element in the tree is called a `Node`, categorized into 3 structural roles:
    1. **Root Node**: The single top-level `Parent` node passed into the `Scene` constructor.
    2. **Branch Nodes**: Container and layout nodes (such as `VBox`, `HBox`, `BorderPane` or `Group`) that hold and position child nodes.
    3. **Leaf Nodes**: Terminal visual elements (such as `Button`, `Label`, `TextField` or `Canvas`) that render content and cannot have children.

![JavaFX Stage, Scene and Scene Graph](../images/Stage_Scene_Graph_JavaFX.png) *JavaFX Stage, Scene and Scene Tree Hierarchy. Source: Google AI*