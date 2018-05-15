import sys

sys.path.append('/usr/local/lib/python2.7/site-packages')

import cv2
import dlib
from skimage import io
import os
detector = dlib.get_frontal_face_detector()
win = dlib.image_window()

img = io.imread("my_file.png")
dets = detector(img, 1)
for i, d in enumerate(dets):
    cv2.rectangle(img, (d.left(), d.top()), (d.right(), d.bottom()), (255, 0, 0), 2)
img = img[:,:,::-1]
cv2.imwrite("output.jpg", img)
print ("Success")
