import speech_recognition as sr

# Function to process the detected wake word
def process_wake_word():
    print("Wake word detected!")
    # Implement your Cram Buddy modification logic here

# Create a recognizer instance
r = sr.Recognizer()

# List of language model files
lm_files = [
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeP1.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeP2.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeP3.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeP4.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeP5.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeN1.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeN2.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeN3.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeN4.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeN5.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeN6.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeN7.wav",
    "/Users/choccoxricco/Documents/CramBud1.1/CramBuddyProgram/Audio/wav/wakeN8.wav"
    # Add more language model files as needed
]

# Use the microphone as the audio source
with sr.Microphone() as source:
    print("Calibrating microphone...")
    r.adjust_for_ambient_noise(source)
    print("Listening for wake word...")

    while True:
        audio = r.listen(source)

        try:
            # Iterate through the language model files
            for lm_file in lm_files:
                # Create a new recognizer instance for each iteration
                r = sr.Recognizer()

                # Use the current language model file
                with sr.AudioFile(lm_file) as lm_source:
                    lm_audio = r.record(lm_source)

                # Use CMUSphinx to recognize the wake word
                text = r.recognize_sphinx(lm_audio)

                if text == "WAKE WORD":
                    process_wake_word()
                    # Start listening for audio commands related to Cram Buddy modifications
                    print("Listening for audio commands...")
                    audio_commands = r.listen(source)

                    # Use CMUSphinx to recognize the audio commands
                    command_text = r.recognize_sphinx(audio_commands)
                    print("Command:", command_text)
                    # Implement logic to process the recognized command and modify your Cram Buddy accordingly
                    break  # Exit the loop once a command is recognized

        except sr.UnknownValueError:
            pass
