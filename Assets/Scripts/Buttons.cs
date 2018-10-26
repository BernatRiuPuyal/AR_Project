using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Buttons : MonoBehaviour {


    public static Buttons instance;

    public Button backButton;
    public Button shareButton;
    public Button fullScreenButton;


	// Use this for initialization
	void Start () {
        backButton.onClick.AddListener(OnBackClick);
        shareButton.onClick.AddListener(OnShareClick);
        fullScreenButton.onClick.AddListener(OnFullScreenClick);


    }

    private void Awake()
    {

    }

    // Update is called once per frame
    void Update () {
		
	}



    private void OnBackClick()
    {
        ExitApp();

    }

    private void ExitApp()
    {
        if (Application.platform == RuntimePlatform.Android)
        {
            AndroidJavaObject activity = new AndroidJavaClass("com.unity3d.player.UnityPlayer").GetStatic<AndroidJavaObject>("currentActivity");
            activity.Call<bool>("moveTaskToBack", true);
        }
        else
        {
            Application.Quit();
        }
    }

    private void OnShareClick()
    {
        Debug.Log("Share click");

#if UNITY_ANDROID
        ShareTextInAnroid();

#else
        Debug.Log("No sharing set up for this platform.");
#endif
    }

#if UNITY_ANDROID
    public void ShareTextInAnroid()
    {

        var shareSubject = "Mira qué bicho me he encontrado!";
        var shareMessage = "Esta app es una pasada! Puedes descargarla desde:" +
        "\nhttps://play.google.com/store/apps/details?id=com.the8thwall.XRRemote";


        if (!Application.isEditor)
        {
            // Create intent for action send
            AndroidJavaClass intentClass = new AndroidJavaClass("android.content.Intent");
            AndroidJavaObject intentObject = new AndroidJavaObject("android.content.Intent");
            intentObject.Call<AndroidJavaObject>("setAction", intentClass.GetStatic<string>("ACTION_SEND"));

            // Put text and subject extra
            intentObject.Call<AndroidJavaObject>("setType", "text/plain");
            intentObject.Call<AndroidJavaObject>("putExtra", intentClass.GetStatic<string>("EXTRA_SUBJECT"), shareSubject);
            intentObject.Call<AndroidJavaObject>("putExtra", intentClass.GetStatic<string>("EXTRA_TEXT"), shareMessage);

            // Call createChooser method of activity class
            AndroidJavaClass unity = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            AndroidJavaObject currentActivity = unity.GetStatic<AndroidJavaObject>("currentActivity");
            AndroidJavaObject chooser = intentClass.CallStatic<AndroidJavaObject>("createChooser", intentObject, "Comparte en...");
            currentActivity.Call("startActivity", chooser);
        }

    }
#endif


    private void OnFullScreenClick()
    {

        //UI_manager.instance.
    }








}


