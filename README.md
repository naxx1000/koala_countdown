# Koala Countdown

<!--- Replace <OWNER> with your Github Username and <REPOSITORY> with the name of your repository. -->
<!--- You can find both of these in the url bar when you open your repository in github. -->
![Workflow result](https://github.com/naxx1000/koala_countdown/workflows/Check/badge.svg)

## :scroll: Description

A simple countdown application (No koalas included).

<div style="text-align: justify">The application has a pause and reset button. The timer can count down from up to 59 minutes and 59 seconds. A shape behind the timer will visually show the user that the timer is changing state (Running, Pausing, Finished) The timer can be set by dragging up or down on either the minutes or seconds column. When dragging, the timer state is paused.</div>

## :bulb: Motivation and Context

This is a submission for the second week of the Jetpack Compose challenge.

<div style="text-align: justify">Having two scrollable columns for both minutes and seconds provided some design challenges For example, since there are upper bounds to each column (0-59) when scrolling through the minutes column, the seconds will not change. But when the upper bound is reached, the seconds will also changed to its upper bound. An initial idea I had for the timer was to change the font type of a digit for every individual tick. When implementing this idea, a problem arose when the different fonts screwed with the size of the parent layouts. This caused the digits to not align with each other, and I decided to scrap it. I wanted to instead use a simpler solution, since I am still not familiar with Compose. This is why I went with a simple box with a RoundedCornerShape inside that changes color and corner size depending on the state of the timer. I also wanted the color to match both dark and light theme, but I had trouble implementing custom attributes to the Theme class. Instead I Material Theme's attributes; error, onError, and secondaryVariant, as my app was not using these colors anyway. Please let me know if there is a way to have more colors than the MaterialTheme, which will also change on light and dark mode. 😊</div>

## :camera_flash: Screenshots and video

<img src="/results/screenshot_1.png" width="230">
&emsp;<img src="/results/screenshot_2.png" width="230">
&emsp;<img src="/results/video_1.gif" width="230">

## License

```
Copyright 2020 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```