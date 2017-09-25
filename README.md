The Android application to view commits in github repositories.


### Requirements ###

* Android Studio 2.3 or higher
* Git
* Java 8

### How do I get set up? ###

* Create a project directory at appropriate location.
* Open terminal and cd to project directory 
* In terminal write **git clone  git@github.com:tanaytandon12/repoViewer.git **
* Open Android Studio
* Select Open an existing Android Studio Project
* Navigate to your project and select OK

### Project Architecture ###

* The project is modelled on the Model-View-Presenter (MVP) pattern.
* **ui** is the view and presenter
* **model** & **data** is the  model
* The networking library used is [Retrofit](http://square.github.io/retrofit/) in conjunction with [RxJava](https://github.com/ReactiveX/RxJava)
* [Greendao](http://greenrobot.org/greendao/) is used for offline storage
* The parent class for Activity, Fragment and,
  DialogFragment are **BaseActivity**, **BaseFragment** and, **BaseDialogFragment**.


### Who do I talk to? ###

* tanay1400089@gmail.com