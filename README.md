
__Code smell__

Code smell, also known as bad smell, in computer programming code, refers to any symptom in the source code of a program that possibly indicates a deeper problem. Smells are certain structures in the code that indicate violation of fundamental design principles and negatively impact design quality




__Planning__


CRC

PhotoRenamer
  + rename image files based on tags  


+ choose directory and view a list of image files under directory
+ display tags
  + select tags
  + add new tags
  + delete existing ones
+ rename image file to include tags
+ search tags
+ open directory containing current image files  

+ tags has a history of names
+ choice of going back to earlier name

+ tags persist when application quit and reopened

+ creation of config files needed...


__Model__


`Tag` (implements `serializable`)
+ R
  + `name`: String
  + `getName`(): String
  + `setName`(String name): void
    + add "@" to `name` and assign to `name`   
+ C


`TagManager`
+ R
  + constructor initialize empty HashMap
  + `Tags`: HashMap<String, Tag>
  + `readFromFile`(filePath: String): void
    + load tags from file; initialize every `Tag` and store in `Tags`
  + `saveToFile`(filePath: String): void
    +  save `Tags` HashMap to file
  + `addTag`(tagName: String): void
    + instantiate `Tag` with name `tagName`
    + add t to `Tags` HashMap
  + `deleteTag`(tagName: String): void
    + remove `Tag` with `tagName` in `Tags` with tag as key
+ C
  + `Tag`


`FileHistory` (interface)
+ `history`: `ArrayList<ArrayList<Time, OldName, NewName>>`
+ `addFileHistory`(tags: Tag[])
  + (timestamp, oldname, newname)
+ `getTimeList()`
  + get `[Time]` from history
+ `getNameList()`
+ `getHistory()`
  + convert `ArrayList<Time, String, String>` to String
  + return `ArrayList<Strings>`



`ImageFile` (extends `ImageFileManager` implements `FileHistory`)
+ R
  + `path`: String
  + `directoryPath`: String
  + `origName`: String
    + original name without
    + assign static
  + `name`: String
  + `tags`: Tag[]   
    + tags associated with ImageFile     
  + `addTag(newTag: Tag)`: void
    + push `tagName` tag to `tags` array
    + call `this.rename(tag)`
    + update `this.path`
  + `removeTag(tagName: String)`: void
    + remove `tagName` from `tags` array
    + call `this.rename(tag[])`
    + update `this.path`


`ImageFileManager`
+ R   
  + `rename`(tag: Tag[]):  
    + call `addFileHistory(tag)`
    + if tag not in `ImageFile.tags`
      + rename file with `this.path + ` to `this.path + tag.getName()`
    + call `log()`
  + `reverName`()
  + `log`
    + in format (oldName, newName, timeStamp)



__VIEW__

`Window`
+ R
  + `buildWindow`: JFrame
    + `imageChooser` button
      + choose
      + binds `FileChooserButtonListener` to button
    + `MainWindow`
      + construct the main window for subcomponents
      + may have to add `WindowListener` to specify on close behavior
    + `ImageWindow`
      + displays current image
    + `ImageFileWindow`
      + displays info about current image
        + name
        + path
        + tags as `ImageFileTagButton`
      + binds `RemoveImageFileTagButtonListener` to every button
    + `TagsWindow`
      + displays all existing tags as `TagButton`
        + binds `AddTagButtonListener` to `TagButton`
      + creates an clickable __X__  icon `DeleteTagButton`
        + binds `DeleteTagButtonListener` to `DeleteTagButton`
      + creates a button `CreateTagButton`
        + binds `CreateTagButtonListener`
    + `LogPopUp`
      + displays `ImageFile.getHistory()`
    + `NameHistoryDropDown`
      + displays `ImageFile.getNameList()`
      +
    + create `new ImageFileManager()`
    + create `new TagManager()`


`ImageChooserButtonListener`
+ R
  + `actionPerformed`:
    + create pop up and prompt user to navigate filesystem and navigate through directory and select an image
    + change view => display one images in window
    + create `new ImageFile(path)`

`CreateTagButtonListener`  
+ R    
  + `actionPerformed`:
    + create pop up and prompt user for input  
    + call `TagManager.addTag(input)`  
+ C  
  + `TagManager`  
  + `Window`

`DeleteTagButtonListener`
+ R
  + `actionPerformed`  
    + call `TagManager.deleteTag()`  


`AddTagButtonListener`
+ R
  + `actionPerformed`
    + call `ImageFile.addTag(tag[])`
    + update view

`RemoveImageFileTagButtonListener`
+ R
  + `actionPerformed`
    + call `ImageFile.removeTag(tag[])`



HashMap
+
