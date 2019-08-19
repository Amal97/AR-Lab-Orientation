from azure.cognitiveservices.vision.customvision.training import CustomVisionTrainingClient
from azure.cognitiveservices.vision.customvision.training.models import ImageFileCreateEntry
import os
import time

ENDPOINT = "https://australiaeast.api.cognitive.microsoft.com/"

# Replace with a valid key
training_key = "2382a5811bf34c099f3d8385d64d13da"
prediction_key = "dcf92e4f48224955a770e3e6e7390e3d"
prediction_resource_id = "/subscriptions/90ac3555-f817-4728-8e02-c5e28a30f349/resourceGroups/test/providers/Microsoft.CognitiveServices/accounts/test_prediction"

publish_iteration_name = "classifyModel"

trainer = CustomVisionTrainingClient(training_key, endpoint=ENDPOINT)

# Create a new project
print ("Creating project...")
project = trainer.create_project("AR-lab")

oscilloscope_tag = trainer.create_tag(project.id, "oscilloscope")
powersupply_tag = trainer.create_tag(project.id, "powersupply")
multimeter_tag = trainer.create_tag(project.id, "multimeter")
waveformgenerator_tag = trainer.create_tag(project.id, "waveformgenerator")
phoebe_tag = trainer.create_tag(project.id, "phoebe")
base_image_url = os.getcwd() + "/"

print("Adding images...")

print("Uploading oscilloscope and power supply")
image_list = []

for image_num in range(0, 16):
    file_name = "oscilloscope{}.jpg".format(image_num)
    with open(base_image_url + "images/oscilloscope/" + file_name, "rb") as image_contents:
        image_list.append(ImageFileCreateEntry(name=file_name, contents=image_contents.read(), tag_ids=[oscilloscope_tag.id]))

for image_num in range(0, 32):
    file_name = "powersupply{}.jpg".format(image_num)
    with open(base_image_url + "images/powersupply/" + file_name, "rb") as image_contents:
        image_list.append(ImageFileCreateEntry(name=file_name, contents=image_contents.read(), tag_ids=[powersupply_tag.id]))

upload_result = trainer.create_images_from_files(project.id, images=image_list)
if not upload_result.is_batch_successful:
    print("Image batch upload failed.")
    for image in upload_result.images:
        if image.status != "OK":
            print(image.source_url)

print("Uploading multimeter")
image_list = []
for image_num in range(0, 55):
    file_name = "multimeter{}.jpg".format(image_num)
    with open(base_image_url + "images/multimeter/" + file_name, "rb") as image_contents:
        image_list.append(ImageFileCreateEntry(name=file_name, contents=image_contents.read(), tag_ids=[multimeter_tag.id]))

upload_result = trainer.create_images_from_files(project.id, images=image_list)
if not upload_result.is_batch_successful:
    print("Image batch upload failed.")
    for image in upload_result.images:
        if image.status != "OK":
            print(image.source_url)

print("Uploading waveform generator")
image_list = []

for image_num in range(0, 61):
    file_name = "waveformgenerator{}.jpg".format(image_num)
    with open(base_image_url + "images/waveformgenerator/" + file_name, "rb") as image_contents:
        image_list.append(ImageFileCreateEntry(name=file_name, contents=image_contents.read(), tag_ids=[waveformgenerator_tag.id]))

upload_result = trainer.create_images_from_files(project.id, images=image_list)
if not upload_result.is_batch_successful:
    print("Image batch upload failed.")
    for image in upload_result.images:
        if image.status != "OK":
            print(image.source_url)

image_list = []

for image_num in range(0, 17):
    file_name = "phoebe{}.jpg".format(image_num)
    with open(base_image_url + "images/phoebe/" + file_name, "rb") as image_contents:
        image_list.append(ImageFileCreateEntry(name=file_name, contents=image_contents.read(), tag_ids=[phoebe_tag.id]))

upload_result = trainer.create_images_from_files(project.id, images=image_list)
if not upload_result.is_batch_successful:
    print("Image batch upload failed.")
    for image in upload_result.images:
        if image.status != "OK":
            print(image.source_url)

