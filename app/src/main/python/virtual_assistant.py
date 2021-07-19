import os
from gtts import gTTS
import speech_recognition as sr
from chatterbot import ChatBot
from chatterbot.trainers import ChatterBotCorpusTrainer
import webbrowser
import datetime


chat_bot = ChatBot(name='Marites', read_only=True, logic_adapters=['chatterbot.logic.MathematicalEvaluation',
                                                                   'chatterbot.logic.BestMatch'],
                   database_uri=None)
trainer = ChatterBotCorpusTrainer(chat_bot)
trainer.train(
    "chatterbot.corpus.filipino"
)


def greet():
    hour = int(datetime.datetime.now().hour)
    if 0 <= hour < 12:
        respond("Magandang Umaga!")
    elif 12 <= hour < 18:
        respond("Magandang Hapon!")
    else:
        respond("Magandang Gabi!")
    respond("Si Marites po ito. Paano ko po kayo matutulungan")


def take_command():
    # Instance of speech recognition
    r = sr.Recognizer()
    # setting the voice pickup and sound settings of the sr object instance
    r.energy_threshold = 150
    r.dynamic_energy_threshold = True
    # takes voice input from user
    with sr.Microphone as source:
        print("Nakikinig...")
        r.adjust_for_ambient_noise(source)
        audio = r.listen(source)

    # processes the voice input of user
    try:
        print("Inaalam...")
        query = r.recognize_google(audio, language='fil-PH')  # Using google speech to text package
        print(f"Sinabi ng gumagamit: {query}\n")  # User query will be printed.
    except Exception as e:
        print("Pakiulit po...")
        query = "Pakiulit po"

    return query


def respond(audio):
    print(audio)
    audio_string = str(audio)
    tts = gTTS(text=audio_string, lang='tl')
    tts.save("reply.mp3")
    os.system("mpg123 reply.mp3")


def voice():
    greet()
    while True:
        query = take_command().lower()  # converts user query into lower case
        # Statements processing user input
        if 'google' in query:
            respond('Pumupunta sa google.com')
            webbrowser.open('www.google.com')
        elif 'facebook' in query:
            respond('pumupunta sa facebook.com')
            webbrowser.open('www.facebook.com')
        elif 'messenger' in query:
            respond('pumupunta sa messenger.com')
            webbrowser.open('www.messenger.com')
        else:
            response = chat_bot.get_response(query)
            respond(response)


def chat(query):
	# Statements processing user input
    if 'google' in query:
        respond('Pumupunta sa google.com')
        webbrowser.open('www.google.com')
    elif 'facebook' in query:
        respond('pumupunta sa facebook.com')
        webbrowser.open('www.facebook.com')
    elif 'messenger' in query:
        respond('pumupunta sa messenger.com')
        webbrowser.open('www.messenger.com')
    else:
        res = chat_bot.get_response(query)
        return res