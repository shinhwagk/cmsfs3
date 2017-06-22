import json

def loadDeployConf():
    with open('remote.json', 'r') as file:
        print(json.loads(file.read()))

loadDeployConf()