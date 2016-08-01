# SnackBarUndo

Library to make the items in a `RecyclerView` dismissable with the possibility to undo
it, using the `SnackBar` to provide this functionality like the Google Messeger app for Android does.



<img src="https://github.com/andela-gkuti/SnackBarUndo/blob/master/snackbarundo/giphy.gif">

**Gradle**

Add the following to your `build.gradle`:

    allprojects {
        repositories {
		    ...
		    maven { url "https://jitpack.io" }
	}

	dependencies {
	        compile 'com.github.andela-gkuti:SnackBarUndo:v1.0'
	}
	
**Maven**:

Add the JitPack repository to your build file

    <repositories>
		  <repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		  </repository>
	 </repositories>
	 
	 <dependency>
	    <groupId>com.github.andela-gkuti</groupId>
	    <artifactId>SnackBarUndo</artifactId>
	    <version>v1.0</version>
	</dependency>


NOTE that the library should be used with a `Cordinator layout` as your root 
GONE, or both the data and the undo layout will be displayed in the row at the same time.


Usage
==============
Create and instance of SnackBarUndo
```java
    SnackBarUndo snackBarUndo = new SnackBarUndo(list,coordinatorLayout,AdapterClass.this);
```

In your project delete method call the snackBarUndo.delete() method and pass in the adapter position using `getAdapterPosition()` and SnackBarUndoCallback()
```java
void delete(){
    snackBarUndo.delete(getAdapterPosition(), new SnackBarUndoCallback() {
        @Override
        public void deleteData() {
            //code to delete the item when undo wasn't clicked
        }
    });
}
```

Contribute
=======

- Feel free to fork it


License
=======

    Copyright 2011, 2015 Kai Liao

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
