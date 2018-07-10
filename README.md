# CheckTime_PatternLock_PIN

I got the base code and img from <a href="https://github.com/aritraroy/PatternLockView" target="_blank">reference site</a> <br>
So the basic license follows thier rules <br>

<hr>
 <table style="margin-left: auto; margin-right: auto;">
  <tr>
   <th>The information of experiment environment</th>
  </tr>
  <tr>
    <th>
    Device: Nexus 5X<br>
    OS: Android<br>
    Version: API28<br>
    Display: 420dpi<br></th>
  </tr>
 </table>
<hr>

A pattern locking technique applied to a Galaxy cell phone is a locking technique that many people use conveniently. However, the safety of the pattern locking technique is very low compared to many others. The pattern locking technique is vulnerable to a shoulder surfing attack, which is based on the user's input and can be interpreted by looking at the movement of the shoulder. Smudge attack is also vulnerable due to fingerprint drag marks remaining on the mobile phone pad. Therefore, in this paper, we want to add a new security method by using the time pressed in the pattern lock technique to secure the vulnerability. It is a method to recognize as a new point according to the time pressed on each point on the pattern keypad of 3x3. It is divided into short, middle and long click according to the time of pressing each point. When dragging using this technique, security probability of 3 ^ n increases. Therefore, even if dragging in the same 'ㄱ' manner, it is a completely different pattern depending on the pressing time at each point.
<br>

<img src="https://github.com/kyu-h/PressTime_PatternLock_PIN/blob/master/img/img.JPG">
This img shows the point have 3 attributes. When using this system on the cell phone the letters which are short click, middle click, long click will not showing because anyone can see how user press. So we changed it vibration. <br>

 <a href="https://youtu.be/OEOkHHQPTgA" target="_blank">Watching for full implementation video</a> <br>

