from bs4 import BeautifulSoup as soup
import calendar
from chatterbot import ChatBot
from chatterbot.trainers import ChatterBotCorpusTrainer
import datetime
from pyowm import OWM
from urllib.request import urlopen

chat_bot = ChatBot(name='Marites', read_only=True,
                   logic_adapters=['chatterbot.logic.MathematicalEvaluation',
                                   'chatterbot.logic.BestMatch'],
                   database_uri=None)
trainer = ChatterBotCorpusTrainer(chat_bot)
trainer.train(
    "chatterbot.corpus.filipino"
)

def chat(query):
    # Statements processing user input
    res = chat_bot.get_response(query)
    res = str(res)

    if res == 'Panahon at temperatura':
        place = 'Philippines'  # generalized to country=Philippines
        api_key = "80ab4d0c29864f3e9e59691955104cb1"  # free OWM api_key
        open_weather_map = OWM(api_key)
        mgr = open_weather_map.weather_manager()
        weather = mgr.weather_at_place(place).weather
        weather_status = weather.detailed_status
        temp = weather.temperature('celsius')
        return ('Magkakaroon ngayon ng %s. Ang temperatura dito ay %0.2f degree celcius' %
                (weather_status, temp['temp_min']))
    elif res == 'balita ngayon':
        news_url = "https://news.google.com/news/rss"
        user = urlopen(news_url)
        html = user.read()
        user.close()
        soup_html = soup(html, "html.parser")
        news_list = soup_html.findAll("item")
        for news in news_list[:5]:
            return "Isang trending na balita ngayon ay: " + news.title.text
    elif res == 'araw ngayon':
        now = datetime.datetime.now()
        my_date = datetime.datetime.today()
        weekday = calendar.day_name[my_date.weekday()]
        monthNum = now.month
        dayNum = now.day
        month_names = ['Enero', 'Pebrero', 'Marso', 'Abril', 'Mayo',
                       'Hunyo', 'Hulyo', 'Agosto', 'Septyembre', 'Oktubre', 'Novbyembre',
                       'Desyembre']
        ordinalNumbers = ['Ika-1', 'Ika-2', 'Ika-3', 'Ika-4', 'Ika-5', 'Ika-6',
                          'Ika-7', 'Ika-8', 'Ika-9', 'Ika-10', 'Ika-11', 'Ika-12',
                          'Ika-13', 'Ika-14', 'Ika-15', 'Ika-16', 'Ika-17',
                          'Ika-18', 'Ika-19', 'Ika-20', 'Ika-21', 'Ika-22',
                          'Ika-23', 'Ika-24', 'Ika-25', 'Ika-26', 'Ika-27',
                          'Ika-28', 'Ika-29', 'Ika-30', 'Ika-31']
        return 'Ngayon ay ' + weekday + ' ' + ordinalNumbers[dayNum - 1] + ' ng ' + month_names[
            monthNum - 1] + '.'
    elif 'oras ngayon' in query:
        time_now = datetime.datetime.now().strftime("%H:%M:%S")
        return "Ang oras ngayon ay " + time_now
    elif res == 'wikipedia':
        return 'pumupunta sa https://wikipedia.com'
    elif res == 'google':
        return 'pumupunta sa https://google.com'
    elif res == 'facebook':
        return 'pumupunta sa https://www.facebook.com'
    elif res == 'messenger':
        return 'pumupunta sa https://messenger.com'
    elif res == 'twitter':
        return 'pumupunta sa https://twitter.com'
    elif res == 'youtube':
        return 'pumupunta sa https://youtube.com'
    elif res == 'amazon':
        return 'pumupunta sa https://amazon.com'
    elif res == 'shopee':
        return 'pumupunta sa https://shopee.ph'
    elif res == 'lazada':
        return 'pumupunta sa https://lazada.com'
    elif res == 'yahoo':
        return 'pumupunta sa https://yahoo.com'
    elif res == 'Netflix':
        return 'pumupunta sa https://netflix.com'
    elif res == 'Instagram':
        return 'pumupunta sa https://instagram.com'
    elif res == 'Google Classroom':
        return 'pumupunta sa https://classroom.google.com'
    elif res == 'Tiktok':
        return 'pumupunta sa https://tiktok.com'
    elif res == 'ABS-CBN News':
        return 'pumupunta sa https://abs-cbn.com'
    else:
        return res
