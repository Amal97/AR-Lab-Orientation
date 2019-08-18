# Python3 code to rename multiple 
# files in a directory or folder 

# importing os module 
import os 

# Function to rename multiple files 
def main(): 
    for folder in os.listdir("images"): 
        i = 0
        for file in os.listdir("images/" + folder): 
            if folder not in file:
                dst = folder + str(i) + ".jpg"
                src = 'images/'+ folder + '/' + file 
                dst = 'images/'+ folder + '/' + dst 
                
                # rename() function will 
                # rename all the files 
                os.rename(src, dst) 
                i += 1
        
    

# Driver Code 
if __name__ == '__main__': 
	
	# Calling main() function 
	main() 
