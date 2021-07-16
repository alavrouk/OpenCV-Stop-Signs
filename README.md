# OpenCV-Stop-Signs

This is mainly me messing around with OpenCV. I don't think my methodology was fantastic, but I did not want to google anything. Instead, I wanted to learn what works and what doesn't work in image processing using trial and error.

The unit test to run all of the stop sign images is a little buggy, I think this comes from the fact that I suck at JUnit

There is a debugging flag for the findStopSigns() method. When it is set to true, the program outputs intermediate images. I did not want to put them all on the same JFrame because awt makes me really really sad. Without debugging disabled, the program outputs a JUnit with the original and the found stop sign outlined with a rectangle side by side.

As far as limitations go
  I resize the image to a constant size for a proper epsilon value, which can sometimes make an originally small image grainy.
  In general, the program only finds stop signs in the foreground or midground. I loose too much precision through processing to detect one in the background.
  A stop sign will not be detected if there is a large red octagon in the background. I did not do anything to detect actual letters in the stop sign.
  I don't want to find hundreds of stop sign photos on the internet. All of the databases I have found from similar projects are all jpgs, but I want to work with rasters, not vector images.

I think I will continue working on this, so hopefully my methodology will be improved and my limitations will be fixed over time.
