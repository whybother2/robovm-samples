/*
 * Copyright (C) 2014 Trillian Mobile AB
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * 
 * Portions of this code is based on Apple Inc's LocateMe sample (v2.2)
 * which is copyright (C) 2008-2010 Apple Inc.
 */

package org.robovm.samples.locateme;

import org.robovm.apple.corelocation.CLLocationManager;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIAlertView;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationDelegateAdapter;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.apple.uikit.UIImage;
import org.robovm.apple.uikit.UINavigationController;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UITabBarController;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.samples.locateme.viewcontrollers.GetLocationViewController;
import org.robovm.samples.locateme.viewcontrollers.TrackLocationViewController;

public class LocateMe extends UIApplicationDelegateAdapter {
    private UIWindow window;
    private UITabBarController tabBarController;
    private GetLocationViewController getLocationViewController;
    private TrackLocationViewController trackLocationViewController;

    @Override
    public boolean didFinishLaunching (UIApplication application, UIApplicationLaunchOptions launchOptions) {
        tabBarController = new UITabBarController();

        getLocationViewController = new GetLocationViewController();
        UINavigationController getLocationController = new UINavigationController(getLocationViewController);
        getLocationController.getTabBarItem().setTitle("Get Location");
        getLocationController.getTabBarItem().setImage(UIImage.createFromBundle("pin.png"));
        tabBarController.addChildViewController(getLocationController);

        trackLocationViewController = new TrackLocationViewController();
        UINavigationController trackLocationController = new UINavigationController(trackLocationViewController);
        trackLocationController.getTabBarItem().setTitle("Track Location");
        trackLocationController.getTabBarItem().setImage(UIImage.createFromBundle("bullseye.png"));
        tabBarController.addChildViewController(trackLocationController);

        // Create a new window at screen size.
        window = new UIWindow(UIScreen.getMainScreen().getBounds());
        // Set our viewcontroller as the root controller for the window.
        window.setRootViewController(tabBarController);
        // Make the window visible.
        window.makeKeyAndVisible();

        if (!CLLocationManager.isLocationServicesEnabled()) {
            UIAlertView servicesDisabledAlert = new UIAlertView(
                "Location Services Disabled",
                "You currently have all location services for this device disabled. If you proceed, you will be asked to confirm whether location services should be reenabled.",
                null, "OK");
            servicesDisabledAlert.show();
        }

        /*
         * Retains the window object until the application is deallocated. Prevents Java GC from collecting the window object too
         * early.
         */
        application.addStrongRef(window);

        return true;
    }

    public static void main (String[] args) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(args, null, LocateMe.class);
        pool.close();
    }
}
