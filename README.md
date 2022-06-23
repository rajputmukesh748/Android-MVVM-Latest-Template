# Android-MVVM-Latest-Template

<h4>Hello Developers.......</h4>
  <p>This android template used only for help to setup android project with all latest features. You just need to change package name and enjoye this template. Hope it's very helpfull to all new users or developers.</p>
  
  <h4>Features</h4>
  <ol>
    <li>Used Kotlin with latest MVVM architecture</li>
    <li>Use JAVA-11 for run this project</li>
    <li>Repository pattern for call REST Api's</li>
    <li>Easy Data Storefor save local data with coroutines</li>
    <li>Easy to use sockets</li>
    <li>Error Handling and Memory Management</li>
    <li>Dexter for handle runtime permissions</li>
    <li>Generic Adapters</li>
    <li>Common extention functions for convert date format, price format, first letter capital</li>
  </ol>
  
  <center><h4>Let's start to introduce all features one by one.</h4></center>
  <p>Whole project used Dependency Injection (Hilt) with View Binding</p>
  
  # Controller Package
  <h5>Controller class</h5>
  <b>1. AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)</b>
  </br>
  <p>This is used for app always show light mode. If your phone used dark mode then your app always used light mode. You can remove this line if you want to add NIGHT mode in your app.</p>
  
  </br></br>
  <b>2. FontsOverride.setDefaultFont(this, "SERIF", "app_fonts.ttf")</b></br>
  <p>This call used for change whole app or all Third Party font. simply pass font name which present in "assets" folder in your app. Automatically change whole app font. If you want to add multiple fonts you can simply add more lines only chnage "SERIF" into different names and also declare in your theme file.</p>
    
  </br></br>
  <b>3. registerActivityLifecycleCallbacks(this)</b></br>
  <p>This line only used for when your current activity change there lifecycle position then you can listen directly in your controller class.</p>
   
   </br></br>
  <b>4. CallDataStore.initializeDataStore(context = applicationContext,dataBaseName = applicationContext.packageName)</b></br>
  <p>This is used for initialize data store. This app used my own dependency for use data store. It's already handles coroutines or singleton. It's easy to use.</p>
  
   </br></br>
  <b>5. FacebookSdk.fullyInitialize() </br> &nbsp;&nbsp;&nbsp;&nbsp;AppEventsLogger.activateApp(this)</b></br>
  <p>This is only for initialize full facebook SDK. If you want to do this then please uncomment it.</p>
  
   </br></br>
  <b>6. Thread.setDefaultUncaughtExceptionHandler(this)</b></br>
  <p>This is used for handle any exception in your app. If your app crash or throw any exception you can direclty handle inside this block and generate a customer report same like Firebase Crashlytics.</p>
  
  
  # Common Classes Package
  <h3>1. Generic Adapter</h3>
  <b>Generic Adapter with DiffUtils or View Binding</b>
  <p>DiffUtil is a utility class that calculates the difference between two lists and outputs a list of update operations that converts the first list into the second one.</p>
    <b><p>How to use Generic Adapter</p></b>

       val adapter = object : GenericAdapter<DemoBinding, String>() {
          override fun onCreateView(parent: ViewGroup, viewType: Int) =
              DemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

          override fun onBindHolder(holder: DemoBinding, dataClass: String) {
              //Hadnle your data or set UI with holder or Data Class
          }
      }
      
<b><p>How pass list in Generic Adapter</p></b>

        adapter.submitList(listOf("Mukesh", "Kotlin", "Code"))
        
<h3>2. GenericFragmentAdapter</h3>
<p>If you want to show multple fragments in viewpager with sliding feature. Then no need to generate a new adapter every time. We provide one generic adapter for handle all this feature. </p>

<b><p>How to use Generic Fragment Adapter</p></b>
    
    viewPagerId.adapter = GenericFragmentAdapter(this).apply{
      //Pass Fragment Classes
      submitList(UpCommingBooking(), PastBooking(), Cancel Booking())
    }
    
    
<h3>3. OtpTimer</h3>
<p>Mostly in every app need to show timer. Basically for OTP 30 second. Everytime it takes time to hanle this. but now it's easy to use. you just set timer </p>

<b><p>How to implemet OTP Timer</p></b>
    <p>First need to implement interface in your class and initialize it.</p>
       
       class VerifyOtp: SendOtpTimerData{ 
          override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            .....
            OtpTimer.sendOtpTimerData = this
          }
        
          override fun otpData(time: String){
            //Here you get time every second
          }
        }
        
<b><p>Change maximum time.</p></b>
          
          OtpTimer.setMaxTime(30, OtpTimeType.SECOND)
          
      
<b><p>Change Time interval.</p></b>
      //Every one second</br>
         
         OtpTimer.setMaxTime(1, OtpTimeType.SECOND)
      
<b><p>OTP Time Types.</p></b>
          
          enum class OtpTimeType{
              // @MILLISECOND [setMaxTime] 30000 for 30 second timer
              MILLISECOND,
              // @SECOND [setMaxTime] 30 for 30 second timer
              SECOND,
              // @MINUTE [setMaxTime] 1 for 1 minute timer 
              MINUTE,
              // @HOURS [setMaxTime] 1 for 1 hour timer
              HOURS,
              // @DAYS [setMaxTime] 1 for 1 day timer
              DAYS
          }
          
          
<b><p>How to start timer</p></b>

          OtpTimer.startTimer()
          
          
          
<b><p>How to stop timer</p></b>

          OtpTimer.stopTimer()
   
