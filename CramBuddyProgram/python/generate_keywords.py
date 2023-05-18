from pocketsphinx import Pocketsphinx

# Path to the wake word text file
wake_word_text_file = 'wake_words.txt'

# Configure Pocketsphinx
config = {
    'keyphrase': 'google',  # Replace with your wake word
    'kws_threshold': 1e-20
}

# Initialize Pocketsphinx
pocketsphinx = Pocketsphinx(**config)

# Generate the wake word keyword file
pocketsphinx.decode(wake_word_text_file)

# Retrieve the configured wake word
keyphrase = config['keyphrase']

# Save the wake word keyword file
with open('wake_words.kws', 'w') as f:
    f.write(f"1.0 {keyphrase} <s>\n")
