# Contextual Cards

- A `Contextual Card` is used to refer to a view that is rendered using json from an API
- These views are dynamic and their properties like images, color, texts, buttons (CTAs) etc. can be changed from backend at anytime.

## SDK


```bash
minSdkVersion 21
targetSdkVersion 30
compileSdkVersion 30
buildToolsVersion "30.0.2"
applicationId "com.fampay.contextualcards"
```

## Tool Versions


```bash
Kotlin - v1.4.21
Gradle build tool - 4.1.2
Gradle Wrapper - 6.5
Android Studio - 4.1.1 Stable
```

## Usage

```kotlin
//Example Xml
//Add this container inside the layout of your activity or fragment
 <com.fampay.contextualcards.contextual_cards.ContextualCardsContainer
        android:id="@+id/contextual_cards"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/toolbar"/>


//From Code get a hold of the view and initialise it by providing the
//context of the activity of fragment in which it is added, should be 
//called from onCreate for initializing only once
 override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_main)
     
     contextual_cards.init(this) 
}

//OR from fragment

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
   super.onViewCreated(view, savedInstanceState)

    fragment_contextual_cards.init(this)
}

```

## Implementation

- Dynamic behaviour of cards is achieved using programmatically
 defining different attributes of the view.
- Different style of cards is supported using different view types of the recyclerview

- For every view type if it supports scrolling then that item is nested with another horizontal recyclerview.

## Plug-And-Play 

- Plug and plug behaviour is achieved by making contextual cards container as custom viewgroup which takes hold of its content and fills in data inside recyclerview through a viewmodel.
- Separate viewmodel is associated with container to deliver data to container based on the lifecycler events of the parent activity/fragment.
- ContextualCardsContainer is made lifecycle-aware using the LifeCycleObserver and observing lifecycle of the activity/fragment.

## Tested Behavior

- On starting activity B from A and if A is having the contextual cards then its paused and resumed as the activity paused and resumed. Same with fragment also.

## TODO

- Gradient functionality
- Text Formatting

## License
[MIT](https://choosealicense.com/licenses/mit/)
