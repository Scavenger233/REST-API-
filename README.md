# REST-API-Project
Building a REST API project from scratch.

## Process
User story -> Multi-layer design Application -> Database design -> REST API design -> build Springboot Project

<img width="1246" alt="image" src="https://github.com/user-attachments/assets/92f053e3-673e-47cf-ab35-957093014961">


<img width="781" alt="image" src="https://github.com/user-attachments/assets/8d363a51-35ff-4472-b3f9-3a79024ecb51">

代码顺序：从底层object entity写起

在 Spring Tool Suite 4 (STS4) 或 Eclipse 中，您可以通过快捷方式快速生成构造函数（constructor）、getter 和 setter 方法。以下是具体步骤和快捷键：

### 1. **生成 Constructor、Getter 和 Setter**

#### 快捷键方式：
1. **将光标放在类中空白的地方**。
2. 按 **Command (⌘) + 1** 调出 **Quick Fix** 菜单。
3. 在弹出的选项中，选择 **Generate Constructor using Fields...** 或 **Generate Getters and Setters...**，根据需要生成构造函数、getter 和 setter。

#### 菜单方式：
1. **右键单击类中的空白处**。
2. 选择 **Source** -> **Generate Constructor using Fields...** 或 **Generate Getters and Setters...**。
3. 在弹出的窗口中，选择要包含在构造函数中的字段，或选择要生成 getter 和 setter 的字段。

### 2. **使用快捷键自动生成**
   - **Command (⌘) + N**：打开生成代码的对话框，选择 **Generate Constructor using Fields...** 或 **Generate Getters and Setters...**。

### 3. **手动快捷生成步骤**
   - **Command (⌘) + 3**：打开搜索框，输入 `generate constructor` 或 `generate getters and setters`，然后选择对应选项，即可快速生成。

### 4. **综合使用 Save Actions 自动化生成**
   如果想进一步自动化某些操作，可以在 **Preferences** -> **Java** -> **Editor** -> **Save Actions** 中设置，勾选自动生成 getter 和 setter。

通过这些快捷方式和方法，您可以快速为类中的字段生成构造函数和 getter/setter，提高开发效率。
