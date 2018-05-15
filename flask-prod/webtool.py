from flask import Flask,redirect
import dlib
import cv2
import sys
import os
import csv
import datetime
import errno
import subprocess
import glob
import math
import numpy as np
from numpy import linalg as LA
from skimage import io

app = Flask(__name__)

@app.route("/markface2/<courseName>")
def markface2(courseName):
	face_rec_model_path = "/var/www/flask-prod/dlib_face_recognition_resnet_model_v1.dat"
	predictor_path = "/var/www/flask-prod/shape_predictor_5_face_landmarks.dat"
	detector = dlib.get_frontal_face_detector()
	sp = dlib.shape_predictor(predictor_path)
	facerec = dlib.face_recognition_model_v1(face_rec_model_path)
	ffff = courseName.split(".")
	dbfaces_folder_path = "/var/www/html/csp203/course/" + ffff[0]
	subprocess.Popen("sudo -u root -S chmod 777 " + dbfaces_folder_path + "/*" + " < /var/www/flask-prod/password.txt" , stdout=subprocess.PIPE, shell=True)
	subprocess.Popen("sudo -u root -S chmod 777 " + dbfaces_folder_path + " < /var/www/flask-prod/password.txt" , stdout=subprocess.PIPE, shell=True)
	img = io.imread("/var/www/html/csp203/android_files/ImageUpload/images/" +  courseName )
	dets = detector(img, 1)
	for i, d in enumerate(dets):
		cv2.rectangle(img, (d.left(), d.top()), (d.right(), d.bottom()), (255, 0, 0), 2)
	img = img[:,:,::-1]
	cv2.imwrite("/var/www/html/csp203/red_marked/output.jpg", img)
	path = dbfaces_folder_path + '/*.txt'
	files = glob.glob(path)
	list_f = []
	list1 = []
	list3 = []
	for name in files:
		try:
		    with open(name) as f:
		    	namm = name.split("/")
		    	nammm = namm[-1].split("_")
		    	list3.append(nammm[0])
		    	data = f.read()
		    	ggg = data.split("\n")
		    	list_f.append(np.array(ggg, dtype=np.float32))
		        
		except IOError as exc:
		    if exc.errno != errno.EISDIR:
		        raise

	for k, d in enumerate(dets):
		shape = sp(img, d)
		face_descriptor = facerec.compute_face_descriptor(img, shape)
		list1.append(face_descriptor)

	label = -1
	output = []
	for f in range(0,len(list1)):
		y = 10000000
		label = -1
		for g in range(0,len(list_f)):
			loss = np.subtract(list1[f],list_f[g])
			x = LA.norm(loss)
			if(x<y):
				y = x;
				label = g;
	
		if(label!=-1):
			if(y<0.5):
				output.append(list3[label])
	out_file = open("/var/www/html/csp203/red_marked/ppl_found.txt" , "w")
	for i in output:
		out_file.write(i)
		out_file.write("\n")
	return "p"  + str(len(list_f[1])) + "p"



 
@app.route("/markface/<courseName>")
def markface(courseName):
	face_rec_model_path = "/var/www/flask-prod/dlib_face_recognition_resnet_model_v1.dat"
	predictor_path = "/var/www/flask-prod/shape_predictor_5_face_landmarks.dat"
	detector = dlib.get_frontal_face_detector()
	sp = dlib.shape_predictor(predictor_path)
	facerec = dlib.face_recognition_model_v1(face_rec_model_path)
	ffff = courseName.split(".")
	dbfaces_folder_path = "/var/www/html/csp203/course/" + ffff[0]
	subprocess.Popen("sudo -u root -S chmod 777 " + dbfaces_folder_path + "/*" + " < /var/www/flask-prod/password.txt" , stdout=subprocess.PIPE, shell=True)
	subprocess.Popen("sudo -u root -S chmod 777 " + dbfaces_folder_path + " < /var/www/flask-prod/password.txt" , stdout=subprocess.PIPE, shell=True)
	img = io.imread("/var/www/html/csp203/android_files/ImageUpload/images/" +  courseName )
	dets = detector(img, 1)
	for i, d in enumerate(dets):
		cv2.rectangle(img, (d.left(), d.top()), (d.right(), d.bottom()), (255, 0, 0), 2)
	img = img[:,:,::-1]
	cv2.imwrite("/var/www/html/csp203/red_marked/output.jpg", img)
	path = dbfaces_folder_path + '/*.txt'
	files = glob.glob(path)
	list_f = []
	list1 = []
	list3 = []
	for name in files:
		try:
		    with open(name) as f:
		    	namm = name.split("/")
		    	nammm = namm[-1].split("_")
		    	list3.append(nammm[0])
		    	data = f.read()
		    	ggg = data.split("\n")
		    	list_f.append(np.array(ggg, dtype=np.float32))
		        
		except IOError as exc:
		    if exc.errno != errno.EISDIR:
		        raise

	for k, d in enumerate(dets):
		shape = sp(img, d)
		face_descriptor = facerec.compute_face_descriptor(img, shape)
		list1.append(face_descriptor)

	label = -1
	output = []
	for f in range(0,len(list1)):
		y = 10000000
		label = -1
		for g in range(0,len(list_f)):
			loss = np.subtract(list1[f],list_f[g])
			x = LA.norm(loss)
			if(x<y):
				y = x;
				label = g;
	
		if(label!=-1):
			if(y<0.5):
				output.append(list3[label])
	out_file = open("/var/www/html/csp203/red_marked/ppl_found.txt" , "w")
	for i in output:
		out_file.write(i)
		out_file.write("\n")
	#ADDING HERE
	
	path="/var/www/html/csp203/course/"+courseName.split(".")[0]+"/"+courseName.split(".")[0]+".csv"
	#print (path)
	all=[]
	with open(path, newline='') as csvfile:
		spamreader = csv.reader(csvfile)
		for row in spamreader:
			#print(row)
			en=row[1]
			set=0
			for i in output:
				if(i==en):
					row.append("1")
					set=1
			if(en=="EntryNumber"):
				row.append(datetime.date.today().strftime("%B %d, %Y"))
				set=1
			if(set==0):		
				row.append("0")
			all.append(row)
	#print(all)
	with open(path, 'w', newline='') as csvfilew:
		spamwriter = csv.writer(csvfilew)
		spamwriter.writerows(all)
	subprocess.Popen("sudo -u root -S chmod 777 " + path + " < /var/www/flask-prod/password.txt" , stdout=subprocess.PIPE, shell=True)
	return redirect("http://localhost/csp203/instructor_portal/index.php", code=302)
	




@app.route("/storeF/<entryNum>")
def storeF(entryNum):

	a = "/var/www/html/csp203/student_images/" + entryNum
	b = a + "/*"
	
	subprocess.Popen("sudo -u root -S chmod 777 " + b + " < /var/www/flask-prod/password.txt" , stdout=subprocess.PIPE, shell=True)
	subprocess.Popen("sudo -u root -S chmod 777 " + a + " < /var/www/flask-prod/password.txt" , stdout=subprocess.PIPE, shell=True)
	face_rec_model_path = "/var/www/flask-prod/dlib_face_recognition_resnet_model_v1.dat"
	predictor_path = "/var/www/flask-prod/shape_predictor_5_face_landmarks.dat"
	detector = dlib.get_frontal_face_detector()
	facerec = dlib.face_recognition_model_v1(face_rec_model_path)
	sp = dlib.shape_predictor(predictor_path)
	dbfaces_folder_path = "/var/www/html/csp203/student_images/" +  entryNum 
	os.makedirs(dbfaces_folder_path + "i")
	write_here = dbfaces_folder_path + "i"
	files_grabbed = []
	types = ["*.png" , "*.jpeg" , "*.jpg"]
	co = 0
	count = 0
	list3 = []
	list2 = []
	for g in glob.glob(os.path.join(dbfaces_folder_path, "*.jpg")):
		count += 1
		img1 = io.imread(g)
		dets1 = detector(img1, 1)
		if(len(dets1)!=1):
			print("Error: Image Labels are ambiguous")
			exit()
		
		list3.append(g)
		for k, d in enumerate(dets1):
			shape = sp(img1, d)
			face_descriptor = facerec.compute_face_descriptor(img1, shape)
			list2.append(face_descriptor)
			y = write_here + "/"
			k = g.split("/")
			y = y + k[-1]
			y = y.replace(".jpeg" , ".txt")
			y = y.replace(".png" , ".txt")
			y = y.replace(".jpg" , ".txt")
			f = open( y , "w")
			f.write(str(face_descriptor))
	return redirect("http://localhost/csp203/student_portal/mycourse.php", code=302)


if __name__ == "__main__":
    app.run(debug=True)
    
    
    

