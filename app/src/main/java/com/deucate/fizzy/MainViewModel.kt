package com.deucate.fizzy

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {

    val actionbarTitle = MutableLiveData<String>()
    val currentFragment = MutableLiveData<Fragment>()
    var currentFragmentID = 8080

}